package org.task.manager.producers;

import org.task.manager.exceptions.TaskCreationException;
import org.task.manager.models.Task;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XMLTaskProducer implements TaskProducers {
    @Override
    public List<Task> getTasks(File file) throws FileNotFoundException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document document = docBuilder.parse(file);
            Element root = document.getDocumentElement();
            if (!root.getTagName().equals("ToDoList")) {
                throw new TaskCreationException("Invalid XML file");
            }
            NodeList nList = root.getChildNodes();

            List<Task> taskList = new ArrayList<>();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    if (!element.getTagName().equals("Task")) {
                        throw new TaskCreationException("Invalid XML file");
                    }
                     taskList.add(new Task(
                            element.getAttribute("id"),
                            element.getAttribute("caption"),
                            foo(element, "Description"),
                            foo(element, "Priority"),
                            foo(element, "Deadline"),
                            foo(element, "Status"),
                            foo(element, "Complete")
                    ));
                }
            }
            return taskList;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (ParserConfigurationException | SAXException | IOException | NullPointerException e) {
            throw new TaskCreationException(e.getMessage());
        }
    }

    private static String foo(Element element, String tagName) { //TODO
        NodeList nodeList = element.getElementsByTagName(tagName);
        return nodeList.getLength() == 0 ? "" : nodeList.item(0).getTextContent();
    }
}
