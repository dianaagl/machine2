package automat;

import automat.Finite.Finite_Automation;
import automat.Millie.Millie_automation;
import automat.Moore.Mour_automation;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Диана on 31.03.2017.
 */
public abstract class Automation {
    private int index;
    private int curr_state;
    private String curr_symbol;
    private int alphabet_size;
    private int states_count;
    private int[][] jump_table;
    private ArrayList<State> States;
    private String[] alphabet;
    private int q0;
    private int[] qn;
    private String[] input_lent;


    public ArrayList<State> getStatesArray(ArrayList<State> states) {
        return States;
    }
    private void writeDocument(Document document, String filename) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(filename);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void SaveToXML(String filename) throws TransformerFactoryConfigurationError, DOMException {
        // Получаем корневой элемент
        try {
            // Создается построитель документа
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.newDocument();

            Node root = document.createElement("structure");
            // Вызываем метод для добавления новой книг


            Element automaton = document.createElement("automaton");
            Element type = document.createElement("type");
            if (this instanceof Finite_Automation) {
                type.setTextContent("fa");
            } else if (this instanceof Mour_automation) {
                type.setTextContent("moore");

            } else if (this instanceof Millie_automation) {
                type.setTextContent("mealy");
            }
            String[] lambda = new String[getStates_count()];
            if (this instanceof Mour_automation) {
                lambda = ((Mour_automation) this).getLambda();
            }
            root.appendChild(type);


            // <Title>
            for (int i = 0; i < getStates_count(); i++) {
                Element state = document.createElement("state");
                state.setAttribute("id", getStates()[i].getId());
                state.setAttribute("name", getStates()[i].getName());

                Element x = document.createElement("x");
                x.setTextContent(String.valueOf((int) getStates()[i].getX()));
                Element y = document.createElement("y");
                y.setTextContent(String.valueOf((int) getStates()[i].getY()));

                state.appendChild(x);

                state.appendChild(y);
                if (this instanceof Mour_automation) {
                    Element output = document.createElement("output");
                    output.setTextContent(lambda[i]);
                    state.appendChild(output);
                }
                automaton.appendChild(state);
            }
            for (int i = 0; i < getAlphabet_size(); i++) {
                for (int j = 0; j < getStates_count(); j++) {
                    if (getJump_table()[i][j] != -1 && getJump_table()[i][j] < getStates_count()) {
                        Element transition = document.createElement("transition");
                        Element from = document.createElement("from");
                        from.setTextContent(String.valueOf(j));
                        Element to = document.createElement("to");
                        to.setTextContent(String.valueOf(getJump_table()[i][j]));
                        Element read = document.createElement("read");
                        read.setTextContent(getAlphabet()[i]);

                        transition.appendChild(from);
                        transition.appendChild(to);
                        transition.appendChild(read);
                        if (this instanceof Mour_automation) {
                            Element transout = document.createElement("transout");
                            transout.setTextContent(((Mour_automation) this).getLambda()[getJump_table()[i][j]]);
                            transition.appendChild(transout);
                        } else if (this instanceof Millie_automation) {
                            Element transout = document.createElement("transout");
                            transout.setTextContent(((Millie_automation) this).getLambda()[i][j]);
                            transition.appendChild(transout);
                        }
                        automaton.appendChild(transition);
                    }
                }
            }
            // Устанавливаем значение текста внутри тега
            root.appendChild(automaton);

            document.appendChild(root);
            writeDocument(document, filename);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        }

        // Создаем новую книгу по элементам
        // Сама книга <Book>

    }

    public Automation LoadAutomation(String filename) {
        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse(filename);
            System.out.print(filename);

            // Получаем корневой элемент
            Element root = document.getDocumentElement();


            NodeList type = root.getElementsByTagName("type");
            String typeContent = type.item(0).getTextContent();

                NodeList states_nodes = root.getElementsByTagName("state");
                NodeList transitions_nodes = root.getElementsByTagName("transition");
                NodeList alfabet_nodes = root.getElementsByTagName("read");
                ArrayList alfabet_list = new ArrayList();

                for (int i = 0; i < alfabet_nodes.getLength(); i++) {
                    if (!alfabet_list.contains(alfabet_nodes.item(i).getTextContent())) {
                        alfabet_list.add(alfabet_nodes.item(i).getTextContent());
                    }
                }

                int alfabet_size = alfabet_list.size();
                int states_size = states_nodes.getLength();
                String[] newAlfabet = new String[alfabet_size];

                String[] newLambda = new String[states_nodes.getLength()];

                for (int i = 0; i < alfabet_size; i++) {
                    newAlfabet[i] = (String) alfabet_list.get(i);
                }

            ArrayList<State> newStates = new ArrayList<State>(states_nodes.getLength());
                int[][] new_jumpTable = new int[alfabet_size][states_size];
            String[][] new_Lambda_mealy = new String[alfabet_size][states_size];

                for (int i = 0; i < states_nodes.getLength(); i++) {
                    NamedNodeMap state_attr = states_nodes.item(i).getAttributes();
                    NodeList x = ((Element) states_nodes.item(i)).getElementsByTagName("x");//.getChildNodes().item(0);
                    System.out.println(x.item(0).getTextContent());

                    NodeList y = ((Element) states_nodes.item(i)).getElementsByTagName("y");//.getChildNodes().item(1);
                    newStates.add(i, new State(state_attr.getNamedItem("id").getNodeValue(),
                            state_attr.getNamedItem("name").getNodeValue(),
                            Integer.parseInt(x.item(0).getTextContent()),
                            Integer.parseInt(y.item(0).getTextContent())
                    ));
                    if (typeContent.equals("moore")) {
                        NodeList output = ((Element) states_nodes.item(i)).getElementsByTagName("output");
                        newLambda[i] = output.item(0).getTextContent();
                    }

                }
                for (int i = 0; i < alfabet_size; i++) {
                    for (int j = 0; j < states_size; j++) {
                        new_jumpTable[i][j] = -1;
                    }
                }
                for (int i = 0; i < alfabet_size; i++) {
                    for (int j = 0; j < states_size; j++) {
                        System.out.println("from " + j + "to " + new_jumpTable[i][j] + "read " + alfabet_list.get(i));
                    }
                }
                for (int i = 0; i < transitions_nodes.getLength(); i++) {

                    Element item = (Element) transitions_nodes.item(i);
                    System.out.println(item.getTextContent());

                    new_jumpTable[alfabet_list.indexOf(item.getElementsByTagName("read").item(0).getTextContent())]
                            [Integer.parseInt(item.getElementsByTagName("from").item(0).getTextContent())] =
                            Integer.parseInt(item.getElementsByTagName("to").item(0).getTextContent());
                    if (this instanceof Millie_automation) {
                        new_Lambda_mealy[alfabet_list.indexOf(item.getElementsByTagName("read").item(0).getTextContent())]
                                [Integer.parseInt(item.getElementsByTagName("from").item(0).getTextContent())] =
                                item.getElementsByTagName("transout").item(0).getTextContent();
                    }

                }
                for (int i = 0; i < alfabet_size; i++) {
                    for (int j = 0; j < states_size; j++) {
                        System.out.println("from " + j + "to " + new_jumpTable[i][j] + "read " + alfabet_list.get(i));
                    }
                }
                this.alphabet_size = alfabet_size;
                this.states_count = states_size;
                this.alphabet = newAlfabet;
                this.States = newStates;
                this.jump_table = new_jumpTable;
            if (this instanceof Finite_Automation) {
                return new Finite_Automation(alfabet_size, states_size, jump_table, newStates.toArray(new State[newStates.size()]), newAlfabet);
            }
                if (this instanceof Mour_automation) {

                    return new Mour_automation(jump_table, newLambda, alphabet, newStates.toArray(new State[newStates.size()]), alfabet_size, states_size);
                }
            if (this instanceof Millie_automation) {
                return new Millie_automation(jump_table, new_Lambda_mealy, newAlfabet, newStates.toArray(new State[newStates.size()]), alfabet_size, states_size);
            }



        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return this;
    }

    abstract public int getIndex();

    abstract public void setIndex(int index);

    abstract public int getCurr_state();

    abstract public void setCurr_state(int curr_state);

    abstract public String getCurr_symbol();

    abstract public void setCurr_symbol(String curr_symbol);

    abstract public int getAlphabet_size();

    abstract public void setAlphabet_size(int alphabet_size);

    abstract public int getStates_count();

    abstract public void setStates_count(int states_count);

    abstract public int[][] getJump_table();

    abstract public void setJump_table(int[][] jump_table);

    abstract public State[] getStates();

    abstract public void setStates(State[] states);

    abstract public String[] getAlphabet();

    abstract public void setAlphabet(String[] alphabet);

    abstract public int getQ0();

    public void setQ0(int q0) {
        this.q0 = q0;
    }

    abstract public int[] getQn();

    public void setQn(int[] qn) {
        this.qn = qn;
    }

    abstract public String[] getInput_lent();

    public void setInput_lent(String[] input_lent) {
        this.input_lent = input_lent;
    }
}
