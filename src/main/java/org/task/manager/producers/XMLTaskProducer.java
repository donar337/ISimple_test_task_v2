package org.task.manager.producers;

import org.task.manager.models.Status;
import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XMLTaskProducer implements TaskProducers {
    private File xmlFile;

    @Override
    public ToDoList getTasks(File file) throws FileNotFoundException {
        this.xmlFile = file;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = docBuilder.parse(file);
            Element root = document.getDocumentElement();
            if (!root.getTagName().equals("ToDoList")) {
                throw new IllegalArgumentException("Invalid XML file");
            }
            NodeList nList = root.getChildNodes();

            ToDoList toDoList = new ToDoList();
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    if (!element.getTagName().equals("Task")) {
                        throw new IllegalArgumentException("Invalid XML file");
                    }
                    Task task = new Task(
                            element.getAttribute("id"),
                            element.getAttribute("caption"),
                            parseElementField(element, "Description"),
                            parseElementField(element, "Priority"),
                            parseElementField(element, "Deadline"),
                            parseElementField(element, "Status"),
                            parseElementField(element, "Complete")
                    );


                    toDoList.add(task, task.getId());
                }
            }
            return toDoList;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (ParserConfigurationException | SAXException | IOException | NullPointerException e) {
            throw new IllegalArgumentException(e.getMessage()); //TODO create adequate exception
        }
    }

    @Override
    public void saveTasks(ToDoList tasks) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document document = docBuilder.newDocument();

            Element root = document.createElement("ToDoList");
            document.appendChild(root);
            tasks.stream().forEach(t -> root.appendChild(taskToXML(document, t)));

            Transformer tf = TransformerFactory.newInstance().newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty(OutputKeys.METHOD, "xml");
            tf.setOutputProperty(OutputKeys.STANDALONE, "no");

            DOMSource domSource = new DOMSource(document);
            StreamResult sr = new StreamResult(xmlFile); //new File("Data.xml")
            tf.transform(domSource, sr);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static String parseElementField(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        return nodeList.getLength() == 0 ? "" : nodeList.item(0).getTextContent();
    }

    private static Element taskToXML(Document document, Task task) {
        Element root = document.createElement("Task");
        root.setAttribute("id", Integer.toString(task.getId()));
        root.setAttribute("caption", task.getCaption());
        root.appendChild(makeAttribute(document, "Description", task.getDescription()));
        root.appendChild(makeAttribute(document, "Priority", Integer.toString(task.getPriority())));
        root.appendChild(makeAttribute(document, "Deadline", task.getDeadline().toString()));
        root.appendChild(makeAttribute(document, "Status", task.getStatus().toString().toLowerCase()));
        if (task.getStatus() == Status.DONE) {
            root.appendChild(makeAttribute(document, "Complete", task.getComplete().toString()));
        }
        return root;
    }

    private static Element makeAttribute(Document document, String tagName, String text) {
        Element element = document.createElement(tagName);
        element.setTextContent(text);
        return element;
    }
}
