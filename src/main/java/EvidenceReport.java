import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class EvidenceReport {

    public static String fileReport;
    public static String imagem;
    public static String elemento;
    public static String nomeTest;
    public static Boolean novoCenario;
    public static String nomeCenario;
    public static String tipoErro;
    public static String detalheErro;
    public static int passo ;
    public static String status = "PASSED";
    public static final String responsavel = "Equipe de Automação";
    public static String path = System.getProperty("user.dir");
    public static final String TITULO = "Relátorio de Automação de Testes - Web";
    private static final LocalDate date = LocalDate.now();
    private static final String dataAtual = date.format(DateTimeFormatter.ofPattern("yyyMMdd"));


    public static String timeStampEvd() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
    }




    public static String capture(WebDriver driver) {
        TakesScreenshot newScreen = (TakesScreenshot) driver;
        String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
        return "data:image/jpg;base64, " + scnShot;

    }

    public static byte[] capture() {
        TakesScreenshot newScreen = (TakesScreenshot) DriverFactory.getDriver();
        return newScreen.getScreenshotAs(OutputType.BYTES);


    }
    public static void gerarRelatorio(String titulo, String acao) {

        File rootsPath = new File(path + "/Evidencias/relatorio/" + dataAtual);
      if (!rootsPath.exists())
            rootsPath.mkdirs();

        fileReport = rootsPath + "/" + nomeTest + ".html";

        try {
            imagem = capture(DriverFactory.getDriver());

            if (novoCenario) {
                passo = 1;
                PrintWriter writer = new PrintWriter(fileReport, String.valueOf(StandardCharsets.UTF_8));
                writer.println("<html>");
                writer.println("<head> <link rel='shortcut icon' href='imagens/iconAndroid.png' />  <link rel='icon' href='imagens/iconAndroid.png' type='image/x-icon' />  <title>" + TITULO + "</title>");
                writer.println("<meta http-equiv=\"Content-Type\"content=\"text/html;charset=utf-8\">");
                writer.println("<style>");
                writer.println(".evidencia{");
                writer.println("border-collapse:collapse;");
                writer.println("border: 1px solid black;");
                writer.println("}");
                writer.println(".Passed{");
                writer.println("color: #39ff14;");
                writer.println("background-color: #000000;");
                writer.println("}");
                writer.println(".Warning{");
                writer.println("color: #FFFF00;");
                writer.println("background-color: #000000;");
                writer.println("}");
                writer.println(".Failed{");
                writer.println("color:#FF0000;");
                writer.println("background-color: #000000;");
                writer.println("}");
                writer.println(".Error{");
                writer.println("color:#FFA500;");
                writer.println("}");
                writer.println("</style>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println("<center><h1><font color='#003366'>Relatório de Execução do Teste:</font><center><br>");
                writer.println("<table>");
                writer.println("<tr>");
                writer.println("<td><img src='data:image/png;base64,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'/></td>");
                writer.println("<td>");
                writer.println("<center><h2>" + TITULO + "<font color='#FA8072'></font></h2><center><br>");
                writer.println("</td>");
                //TODO: Alinha imagem definitiva
                //  writer.println("<td><img src='data:image/png;base64,'/></td>");
                writer.println("</tr>");
                writer.println("</table>");
                writer.println("</p>");
                writer.println("<center><h5>Cenário: <font color='#00AA00'>" + nomeCenario + "</font><center><br>"); //Nova Linha

                // INICIO BLOCO CUSTOMIZAÇÃO PARA AMBIENTE (06/02/2020)
                writer.println("<table border=5 colspan=5 >");
                writer.println("<tr class='evidencia'>");
                writer.println("<td class='evidencia' colspan=4 ><b><center>AMBIENTE</center></b></td></tr><tr>");
                writer.println("<tr class='evidencia'>");
                writer.println("<td class='evidencia' colspan=3  > BROWSER: </td><td class='evidencia' style='width:470px' >" + DriverFactory.setBrowser() + "</td></tr><tr>");
                writer.println("<tr class='evidencia'>");
                writer.println("<td class='evidencia' colspan=3 > SISTEMA OPERACIONAL: </td><td class='evidencia' >" + System.getProperty("os.name") + "</td></tr><tr>");
                writer.println("<tr class='evidencia'>");
                writer.println("<td class='evidencia' colspan=3 > AMBIENTE: </td><td class='evidencia' >HOMOLOGAÇÃO</td></tr><tr>");
                writer.println("<tr class='evidencia'>");
                writer.println("<td class='evidencia' colspan=3 > VERSÃO EM TEST: </td><td class='evidencia' >EM TEST</td></tr><tr>");
                writer.println("<tr class='evidencia'>");
                writer.println("<td class='evidencia' colspan=3 > VERSÃO APLICATIVO: </td>");
                writer.println("<td class='evidencia' colspan=3 >EM TEST</td></tr><tr></table></p>");
                // FIM BLOCO CUSTOMIZAÇÃO PARA AMBIENTE


                writer.println("<table class='evidencia' >");
                writer.println("<tr class='evidencia'>");
                writer.println("<td class='evidencia' colspan=4 ><b><center>" + titulo + "</center></b></td>");
                writer.println("</tr>");
                writer.println("<tr>");
                writer.println("<td class='evidencia' > PASSO: </td><td class='evidencia' >" + passo + "</td>");
                writer.println("<td class='evidencia' > DATA/HORA EXECUÇÃO: </td><td class='evidencia' >"
                        + timeStampEvd() + "</td>");
                writer.println("</tr>");
                writer.println("<tr>");
                writer.println("<td class='evidencia' > AÇÃO: </td><td class='evidencia' colspan=3 >"
                        + acao + "</td>");
                writer.println("</tr>");
                writer.println("<tr>");
                writer.println("<td class='evidencia' > STATUS: </td><td class='evidencia' ><b class='" + status + "' >"
                        + status + "</b></td>");
                writer.println(
                        "<td class='evidencia' > Responsável: </td><td class='evidencia' >" + responsavel + "</td>");
                writer.println("</tr>");
                writer.println("<tr>");
                if (status.equals("FAILED")) {
                    writer.println("<tr>");
                    writer.println("<td class='evidencia' > ERRO: </td><td class='evidencia' ><b class='" + tipoErro + "' >"
                            + tipoErro + "</b></td>");
                    writer.println(
                            "<td class='evidencia' > Detalhe do Erro: </td><td class='evidencia' >" + detalheErro + "</td>");
                    writer.println("</tr>");
                    writer.println("<tr>");
                    writer.println("<td class='evidencia' > ELEMENTO: </td><td class='evidencia' colspan=3 >"
                            + elemento + "</td>");
                    writer.println("</tr>");
                }
                writer.println("<tr>");
                writer.println("<td colspan=4 class='evidencia' ><center>EVIDÊNCIA</center></td>");
                writer.println("</tr>");
                writer.println("<tr>");
                writer.println("<td colspan=4 class='evidencia' ><img src='" + imagem
                        + "' style= 'max-width: 100%;'width:1460px;height:1080px;' /></td>");
                writer.println("</tr>");
                writer.println("</table>");
                writer.println("</p>");
                writer.close();
                novoCenario = false;
            } else {

                PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileReport,  true)));
                writer.println("</p>");
                writer.println("<meta http-equiv=\"Content-Type\"content=\"text/html;charset=utf-8\">");
                writer.println("<table class='evidencia' >");
                writer.println("<tr class='evidencia'>");
                writer.println("<td class='evidencia' colspan=4 > <center><b class='" + titulo + "' >"
                        + titulo + "</center></b></td>");
                // writer.println("<td class='evidencia' colspan=4 ><b><center>" + titulo + "</center></b></td>");
                writer.println("</tr>");
                writer.println("<tr>");
                writer.println("<td class='evidencia' > PASSO: </td><td class='evidencia' >" + passo + "</td>");

                writer.println("<td class='evidencia' > DATA/HORA EXECUÇÃO: </td><td class='evidencia' >"
                        + timeStampEvd() + "</td>");
                writer.println("</tr>");
                writer.println("<tr>");
                writer.println("<td class='evidencia' > AÇÃO: </td><td class='evidencia' colspan=3 >"
                        + acao + "</td>");
                writer.println("</tr>");
                writer.println("<tr>");
                writer.println("<td class='evidencia' > STATUS: </td><td class='evidencia' ><b class='" + status + "' >"
                        + status + "</b></td>");
                writer.println(
                        "<td class='evidencia' > Responsável: </td><td class='evidencia' >" + responsavel + "</td>");
                writer.println("</tr>");
                if (status.equals("FAILED")) {
                    writer.println("<tr>");
                    writer.println("<td class='evidencia' > ERRO: </td><td class='evidencia' ><b class='" + tipoErro + "' >"
                            + tipoErro + "</b></td>");
                    writer.println(
                            "<td class='evidencia' > Detalhe do Erro: </td><td class='evidencia' >" + detalheErro + "</td>");
                    writer.println("</tr>");
                    writer.println("<tr>");
                    writer.println("<td class='evidencia' > ELEMENTO: </td><td class='evidencia' colspan=3 >"
                            + elemento + "</td>");
                    writer.println("</tr>");
                }
                writer.println("<tr>");
                writer.println("<td colspan=4 class='evidencia' ><center>EVIDÊNCIA</center></td>");
                writer.println("</tr>");
                writer.println("<tr>");
                writer.println("<td colspan=4 class='evidencia' ><img src='" + imagem
                       + "' style='max-width: 100%;'width:1460px;height:1080px;' /></td>");
                writer.println("</tr>");
                writer.println("</table>");
                writer.println("</p>");
                writer.close();
            }
            if (passo > 1) {
                PrintWriter writer = new PrintWriter(
                        new BufferedWriter(new FileWriter(fileReport, true)));
                writer.println("</BODY>");
                writer.println("</HTML>");
                writer.close();

            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }

        status = "PASSED";
        passo++;
        DriverFactory.log.info("[Passo evidenciado: " + acao + "] ");
    }


}