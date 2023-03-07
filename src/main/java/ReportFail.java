
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.io.ByteArrayInputStream;


import static org.apache.commons.lang3.StringUtils.substringBetween;


public class ReportFail implements TestWatcher {

    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
        // do something
    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        EvidenceReport.status = "FAILED";
        EvidenceReport.tipoErro = throwable.getClass().getSimpleName();
        EvidenceReport.elemento = substringBetween(String.valueOf(throwable.getCause()), "{", "}");
        if (EvidenceReport.elemento == null)
            EvidenceReport.elemento = substringBetween(String.valueOf(throwable.getMessage()), "{", "}");
        if (EvidenceReport.elemento == null)
            EvidenceReport.elemento = throwable.getMessage();

        switch (EvidenceReport.tipoErro){

            case "ElementNotVisibleException":
                EvidenceReport.detalheErro = "O Elemento está presente mas não vísivel";
                break;
            case "NoSuchElementException":
                EvidenceReport.detalheErro =   "O Elemento não está presente na pagina";
                break;
            case "StaleElementReferenceException":
                EvidenceReport.detalheErro =   "O Elemento foi encontrado, porém velocidade da automação está muito alta";
                break;
            case "TimeoutException":
                EvidenceReport.detalheErro =   " O Tempo definido para esperar o elemento não foi suficiente";
                break;
            case "ElementClickInterceptedException":
                EvidenceReport.detalheErro =   " O elemento que recebe os eventos está ocultando";
                break;
            case "AssertionError":
                EvidenceReport.detalheErro =   "Ocorreu uma divergencia entre o resultado Esperado x Obtido";
                break;
            default:
                EvidenceReport.detalheErro = throwable.getClass().getSimpleName();
                break;
        }
        EvidenceReport.gerarRelatorio("WARNING","Ops! ocorreu uma falha no seu teste ;( ");
        Allure.addAttachment(EvidenceReport.detalheErro, new ByteArrayInputStream(EvidenceReport.capture()));

    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
        // do something
    }
}
