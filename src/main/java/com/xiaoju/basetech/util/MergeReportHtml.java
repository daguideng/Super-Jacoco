package com.xiaoju.basetech.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @description:
 * @author: gaoweiwei_v
 * @time: 2019/12/12 8:41 AM
 */
public class MergeReportHtml {
    public static Integer[] mergeHtml(ArrayList<String> fileList, String destFile) {
        Integer[] result=new Integer[3];
        result[0]=0;
        result[1]=-1;
        result[2]=-1;
        String htmlSchema = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\" />\n" +
                "    <style>\n" +
                "        body, td {\n" +
                "            font-family:sans-serif;\n" +
                "            font-size:10pt;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            font-weight:bold;\n" +
                "            font-size:18pt;\n" +
                "        }\n" +
                "        .breadcrumb {\n" +
                "            border:#d6d3ce 1px solid;\n" +
                "            padding:2px 4px 2px 4px;\n" +
                "        }\n" +
                "        .breadcrumb .info {\n" +
                "            float:right;\n" +
                "        }\n" +
                "        .breadcrumb .info a {\n" +
                "            margin-left:8px;\n" +
                "        }\n" +
                "        .el_report {\n" +
                "            padding-left:18px;\n" +
                "            background-image:url(./common-security/jacoco-resources/report.gif);\n" +
                "            background-position:left center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        .el_group {\n" +
                "            padding-left:18px;\n" +
                "            background-image:url(./common-security/jacoco-resources/group.gif);\n" +
                "            background-position:left center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        .el_bundle {\n" +
                "            padding-left:18px;\n" +
                "            background-image:url(./common-security/jacoco-resources/bundle.gif);\n" +
                "            background-position:left center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        .el_package {\n" +
                "            padding-left:18px;\n" +
                "            background-image:url(./common-security/jacoco-resources/package.gif);\n" +
                "            background-position:left center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        .el_class {\n" +
                "            padding-left:18px;\n" +
                "            background-image:url(./common-security/jacoco-resources/class.gif);\n" +
                "            background-position:left center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        .el_source {\n" +
                "            padding-left:18px;\n" +
                "            background-image:url(./common-security/jacoco-resources/source.gif);\n" +
                "            background-position:left center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        .el_method {\n" +
                "            padding-left:18px;\n" +
                "            background-image:url(./common-security/jacoco-resources/method.gif);\n" +
                "            background-position:left center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        .el_session {\n" +
                "            padding-left:18px;\n" +
                "            background-image:url(./common-security/jacoco-resources/session.gif);\n" +
                "            background-position:left center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        table.coverage {\n" +
                "            empty-cells:show;\n" +
                "            border-collapse:collapse;\n" +
                "        }\n" +
                "        table.coverage thead {\n" +
                "            background-color:#e0e0e0;\n" +
                "        }\n" +
                "        table.coverage thead td {\n" +
                "            white-space:nowrap;\n" +
                "            padding:2px 14px 0px 6px;\n" +
                "            border-bottom:#b0b0b0 1px solid;\n" +
                "        }\n" +
                "        table.coverage thead td.bar {\n" +
                "            border-left:#cccccc 1px solid;\n" +
                "        }\n" +
                "        table.coverage thead td.ctr1 {\n" +
                "            text-align:right;\n" +
                "            border-left:#cccccc 1px solid;\n" +
                "        }\n" +
                "        table.coverage thead td.ctr2 {\n" +
                "            text-align:right;\n" +
                "            padding-left:2px;\n" +
                "        }\n" +
                "        table.coverage thead td.sortable {\n" +
                "            cursor:pointer;\n" +
                "            background-image:url(./common-security/jacoco-resources/sort.gif);\n" +
                "            background-position:right center;\n" +
                "            background-repeat:no-repeat;\n" +
                "        }\n" +
                "        table.coverage thead td.up {\n" +
                "            background-image:url(./common-security/jacoco-resources/up.gif);\n" +
                "        }\n" +
                "        table.coverage thead td.down {\n" +
                "            background-image:url(./common-security/jacoco-resources/down.gif);\n" +
                "        }\n" +
                "        table.coverage tbody td {\n" +
                "            white-space:nowrap;\n" +
                "            padding:2px 6px 2px 6px;\n" +
                "            border-bottom:#d6d3ce 1px solid;\n" +
                "        }\n" +
                "        table.coverage tbody tr:hover {\n" +
                "            background: #f0f0d0 !important;\n" +
                "        }\n" +
                "        table.coverage tbody td.bar {\n" +
                "            border-left:#e8e8e8 1px solid;\n" +
                "        }\n" +
                "        table.coverage tbody td.ctr1 {\n" +
                "            text-align:right;\n" +
                "            padding-right:14px;\n" +
                "            border-left:#e8e8e8 1px solid;\n" +
                "        }\n" +
                "        table.coverage tbody td.ctr2 {\n" +
                "            text-align:right;\n" +
                "            padding-right:14px;\n" +
                "            padding-left:2px;\n" +
                "        }\n" +
                "        table.coverage tfoot td {\n" +
                "            white-space:nowrap;\n" +
                "            padding:2px 6px 2px 6px;\n" +
                "        }\n" +
                "        table.coverage tfoot td.bar {\n" +
                "            border-left:#e8e8e8 1px solid;\n" +
                "        }\n" +
                "        table.coverage tfoot td.ctr1 {\n" +
                "            text-align:right;\n" +
                "            padding-right:14px;\n" +
                "            border-left:#e8e8e8 1px solid;\n" +
                "        }\n" +
                "        table.coverage tfoot td.ctr2 {\n" +
                "            text-align:right;\n" +
                "            padding-right:14px;\n" +
                "            padding-left:2px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top:20px;\n" +
                "            border-top:#d6d3ce 1px solid;\n" +
                "            padding-top:2px;\n" +
                "            font-size:8pt;\n" +
                "            color:#a0a0a0;\n" +
                "        }\n" +
                "        .footer a {\n" +
                "            color:#a0a0a0;\n" +
                "        }\n" +
                "        .right {\n" +
                "            float:right;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <title>manualDiffCoverageReport</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"breadcrumb\" id=\"breadcrumb\"><span class=\"info\"><a href=\"jacoco-sessions.html\" class=\"el_session\">Sessions</a></span><span class=\"el_report\">manualDiffCoverageReport</span></div>\n" +
                "    <h1>manualDiffCoverageReport</h1>\n" +
                "    <table class=\"coverage\" cellspacing=\"0\" id=\"coveragetable\">\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <td class=\"sortable\" id=\"a\">Element</td>\n" +
                "                <td class=\"down sortable bar\" id=\"b\">Missed Instructions</td>\n" +
                "                <td class=\"sortable ctr2\" id=\"c\">Cov.</td>\n" +
                "                <td class=\"sortable bar\" id=\"d\">Missed Branches</td>\n" +
                "                <td class=\"sortable ctr2\" id=\"e\">Cov.</td>\n" +
                "                <td class=\"sortable ctr1\" id=\"f\">Missed</td>\n" +
                "                <td class=\"sortable ctr2\" id=\"g\">Cxty</td>\n" +
                "                <td class=\"sortable ctr1\" id=\"h\">Missed</td>\n" +
                "                <td class=\"sortable ctr2\" id=\"i\">Lines</td>\n" +
                "                <td class=\"sortable ctr1\" id=\"j\">Missed</td>\n" +
                "                <td class=\"sortable ctr2\" id=\"k\">Methods</td>\n" +
                "                <td class=\"sortable ctr1\" id=\"l\">Missed</td>\n" +
                "                <td class=\"sortable ctr2\" id=\"m\">Classes</td>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "        </tbody>\n" +
                "<tfoot></tfoot>"+
                "    </table>\n" +
                "    <div class=\"footer\"><span class=\"right\">Created with <a href=\"http://www.jacoco.org/jacoco\">JaCoCo</a> 1.0.1.201909190214</span></div>\n" +
                "</body>\n" +
                "</html>";
        try {
            Document docSchema = Jsoup.parse(htmlSchema);
            Integer[] array = new Integer[15];
            array[0] = 0;
            array[1] = 0;
            array[2] = 0;
            array[3] = 0;
            array[4] = 0;
            array[5] = 0;
            array[6] = 0;
            array[7] = 0;
            array[8] = 0;
            array[9] = 0;
            array[10] = 0;
            array[11] = 0;
            array[12] = 0;
            array[13] = 0;
            array[14] = 0;
            Element tbodySchema = docSchema.getElementsByTag("table").first();
            for (String fileName : fileList) {
                File file=new File(fileName);
                String module=new File(file.getParent()).getName();
                Document docc = Jsoup.parse(new File(fileName), "UTF-8", "");
                String htmlContent = docc.toString()
                        .replace("<a href=\"", "<a href=\""+module+"/")
                        .replace("src=\"jacoco-resources/", "src=\""+module+"/jacoco-resources/");
                Document doc=Jsoup.parse(htmlContent);
                if(doc.getElementsByTag("tbody").first()==null){
                    continue;
                }
                Elements trs = doc.getElementsByTag("tbody").first().getElementsByTag("tr");
                for (Element ele : trs) {
                    tbodySchema.getElementsByTag("tbody").first().append(ele.html());
                }
                String[] a = doc.getElementsByTag("tfoot").first().child(0).text().split(" ");
                array[1] = array[1] + Integer.parseInt(a[1].replace(",", ""));
                array[2] = array[2] + Integer.parseInt(a[3].replace(",", ""));
                //array[3] = array[3] + Integer.parseInt(a[4].replace("%", ""));
                array[4] = array[4] + Integer.parseInt(a[5].replace(",", ""));
                array[5] = array[5] + Integer.parseInt(a[7].replace(",", ""));
                //array[6] = array[6] + Integer.parseInt(a[8].replace("%", ""));
                array[7] = array[7] + Integer.parseInt(a[9].replaceAll(",",""));
                array[8] = array[8] + Integer.parseInt(a[10].replace(",", ""));
                array[9] = array[9] + Integer.parseInt(a[11].replace(",", ""));
                array[10] = array[10] + Integer.parseInt(a[12].replace(",", ""));
                array[11] = array[11] + Integer.parseInt(a[13].replace(",", ""));
                array[12] = array[12] + Integer.parseInt(a[14].replace(",", ""));
                array[13] = array[13] + Integer.parseInt(a[15].replace(",", ""));
                array[14] = array[14] + Integer.parseInt(a[16].replace(",", ""));
            }
            if(array[2]==0){
                array[1]=1;
                array[2]=1;
            }
            if(array[5]==0){
                array[4]=1;
                array[5]=1;
            }
            if(array[10]==0){
                array[9]=1;
                array[10]=1;
            }
            String tfoot = "         <tr>\n" +
                    "                <td>Total</td>\n" +
                    "                <td class=\"bar\">" + array[1] + " of " + array[2] + "</td>\n" +
                    "                <td class=\"ctr2\">" + (array[2]-array[1])*100/array[2] + "%</td>\n" +
                    "                <td class=\"bar\">" + array[4] + " of " + array[5] + "</td>\n" +
                    "                <td class=\"ctr2\">" + (array[5]-array[4])*100/array[5] + "%</td>\n" +
                    "                <td class=\"ctr1\">" + array[7] + "</td>\n" +
                    "                <td class=\"ctr2\">" + array[8] + "</td>\n" +
                    "                <td class=\"ctr1\">" + array[9] + "</td>\n" +
                    "                <td class=\"ctr2\">" + array[10] + "</td>\n" +
                    "                <td class=\"ctr1\">" + array[11] + "</td>\n" +
                    "                <td class=\"ctr2\">" + array[12] + "</td>\n" +
                    "                <td class=\"ctr1\">" + array[13] + "</td>\n" +
                    "                <td class=\"ctr2\">" + array[14] + "</td>\n" +
                    "            </tr>\n";
            tbodySchema.getElementsByTag("tfoot").first().append(tfoot);
            FileWriter writer = new FileWriter(destFile);
            writer.write(docSchema.toString());
            writer.flush();
            result[0]=1;
            result[1]=(array[5]-array[4])*100/array[5];
            result[2]=(array[10]-array[9])*100/array[10];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}