package automat;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

/**
 * Created by Диана on 28.04.2017.
 */
public class Finite_Automation {
    int alphabet_size;
    int states_count;
    int [][] jump_table;
    State [] States;
    String []alphabet;
    private void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream("other.xml");
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
    public void SaveToXML() throws TransformerFactoryConfigurationError, DOMException {
        // Получаем корневой элемент
        try {
            // Создается построитель документа
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder        db  = dbf.newDocumentBuilder();
            Document               document = db.newDocument();

            Node root = document.createElement("structure");
            // Вызываем метод для добавления новой книг
            Element br = document.createElement("br");
            root.appendChild(br);
            Element automaton = document.createElement("automaton");
            Element type = document.createElement("type");
            type.setTextContent("fa");
            root.appendChild(type);
            root.appendChild(br);

            // <Title>
            for(int i = 0;i < states_count;i++){
                Element state = document.createElement("state");
                state.setAttribute("id",States[i].getId());
                state.setAttribute("name",States[i].getName());
                    Element x = document.createElement("x");
                    x.setTextContent(String.valueOf(States[i].getX()));
                    Element y = document.createElement("y");
                    y.setTextContent(String.valueOf(States[i].getY()));
                    state.appendChild(x);
                    state.appendChild(br);
                    state.appendChild(y);
                automaton.appendChild(state);
            }
            for(int i = 0;i < alphabet_size;i++){
                for(int j = 0;j < states_count;j++){
                    if(jump_table[i][j] != 0 && jump_table[i][j] < states_count + 1){
                        Element transition = document.createElement("transition");
                            Element from = document.createElement("from");
                            from.setTextContent(String.valueOf(j));
                            Element to = document.createElement("to");
                            to.setTextContent(String.valueOf(jump_table[i][j] - 1));
                            Element read = document.createElement("read");
                            read.setTextContent(alphabet[i]);
                        transition.appendChild(from);
                        transition.appendChild(to);
                        transition.appendChild(read);

                        automaton.appendChild(transition);
                    }
                }
            }
            // Устанавливаем значение текста внутри тега
            root.appendChild(automaton);

            document.appendChild(root);
            writeDocument(document);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        }

        // Создаем новую книгу по элементам
        // Сама книга <Book>

    }

    public Finite_Automation(int alphabet_size, int states_count, int[][] jump_table, State[] states, String[] alphabet) {
        this.alphabet_size = alphabet_size;
        this.states_count = states_count;
        this.jump_table = jump_table;
        States = states;
        this.alphabet = alphabet;
    }

    public int getAlphabet_size() {
        return alphabet_size;
    }

    public void setAlphabet_size(int alphabet_size) {
        this.alphabet_size = alphabet_size;
    }

    public int getStates_count() {
        return states_count;
    }

    public void setStates_count(int states_count) {
        this.states_count = states_count;
    }

    public int[][] getJump_table() {
        return jump_table;
    }

    public void setJump_table(int[][] jump_table) {
        this.jump_table = jump_table;
    }

    public State[] getStates() {
        return States;
    }

    public void setStates(State[] states) {
        States = states;
    }

    public String[] getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String[] alphabet) {
        this.alphabet = alphabet;
    }
}
