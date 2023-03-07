package br.com.qa.utils.reports;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import java.io.ByteArrayInputStream;


import static br.com.qa.utils.reports.EvidenceReport.*;
import static org.apache.commons.lang3.StringUtils.substringBetween;


public class ReportFail implements TestWatcher {

    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
        // do something
    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        status = "FAILED";
        tipoErro = throwable.getClass().getSimpleName();
        elemento = substringBetween(String.valueOf(throwable.getCause()), "{", "}");
        if (elemento == null)
            elemento = substringBetween(String.valueOf(throwable.getMessage()), "{", "}");
        if (elemento == null)
            elemento = throwable.getMessage();

        switch (tipoErro){

            case "ElementNotVisibleException":
                detalheErro = "O Elemento está presente mas não vísivel";
                break;
            case "NoSuchElementException":
                detalheErro =   "O Elemento não está presente na pagina";
                break;
            case "StaleElementReferenceException":
                detalheErro =   "O Elemento foi encontrado, porém velocidade da automação está muito alta";
                break;
            case "TimeoutException":
                detalheErro =   " O Tempo definido para esperar o elemento não foi suficiente";
                break;
            case "ElementClickInterceptedException":
                detalheErro =   " O elemento que recebe os eventos está ocultando";
                break;
            case "AssertionError":
                detalheErro =   "Ocorreu uma divergencia entre o resultado Esperado x Obtido";
                break;
            default:
                detalheErro = throwable.getClass().getSimpleName();
                break;
        }
        gerarRelatorio("WARNING","Ops! ocorreu uma falha no seu teste ;( ");
        Allure.addAttachment(detalheErro, new ByteArrayInputStream(EvidenceReport.capture()));

    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
        // do something
    }
}
