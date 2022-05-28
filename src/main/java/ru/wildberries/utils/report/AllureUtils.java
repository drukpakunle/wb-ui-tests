package ru.wildberries.utils.report;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.wildberries.config.TestConfig;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AllureUtils {

    public void addEnvironmentParamsInReport() {
        log.info("Transform Allure Report: Add Environment parameters");

        Map<String, String> params = new HashMap<>();
        params.put("Environment URL", TestConfig.environment.siteUrl.toString());
        params.put("Threads", String.valueOf(TestConfig.threadCount));
        params.put("Device", TestConfig.device.name);
        params.put("Locale", TestConfig.environment.locale.name());

        AllureHtmlElement allureHtmlElement = new AllureHtmlElement() {{
            elementName = "environment";
            elementParams = params;
        }};

        try {
            addElementInReport(allureHtmlElement);
        } catch (Exception | Error e) {
            log.error("Error adding Environment parameters in Allure Report");
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    private void addElementInReport(AllureHtmlElement allureHtmlElement)
            throws ParserConfigurationException, TransformerException {

        log.info("Add Element '{}' in report", allureHtmlElement.elementName);
        final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        final Element element = document.createElement(allureHtmlElement.elementName);
        document.appendChild(element);

        allureHtmlElement.elementParams.forEach((key, value) -> {
            log.info("Add parameter [{}, {}] in report", key, value);
            Element parameterRoot = document.createElement(AllureHtmlTag.PARAMETER.name);
            element.appendChild(parameterRoot);

            Element parameterKey = document.createElement(AllureHtmlTag.KEY.name);
            parameterKey.appendChild(document.createTextNode(key));
            parameterRoot.appendChild(parameterKey);

            Element parameterValue = document.createElement(AllureHtmlTag.VALUE.name);
            parameterValue.appendChild(document.createTextNode(value));
            parameterRoot.appendChild(parameterValue);
        });

        String xmlReportElementPath = String.format("target/allure-results/%s.xml", allureHtmlElement.elementName);
        StreamResult result = new StreamResult(new File(xmlReportElementPath));

        DOMSource domSource = new DOMSource(document);
        TransformerFactory.newInstance().newTransformer().transform(domSource, result);
    }

}