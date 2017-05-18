package graph;

import automat.Automation;
import automat.Finite.Finite_Automation;
import automat.Millie.Millie_automation;
import automat.Moore.Mour_automation;
import automat.ShopAut.Key;
import automat.ShopAut.Shop_automation;
import automat.ShopAut.Takt;
import automat.State;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.handler.mxRubberband;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.*;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.swing.*;
import java.util.*;

/**
 * Created by Диана on 28.04.2017.
 */
public class graph {
    public static class Graph extends JFrame {
        private mxGraph graph;
        private mxGraphComponent graphComponent;
        private Object parent;
        private mxCell[] vert;

        public Graph(Automation aut) {
            super("Automation Graph");

            Document xmlDocument = mxUtils.createDocument();


            graph = new mxGraph() {
                // Overrides method to disallow edge label editing


                // Overrides method to provide a cell label in the display
                public String convertValueToString(Object cell) {
                    if (cell instanceof mxCell) {
                        Object value = ((mxCell) cell).getValue();

                        if (value instanceof Element) {
                            Element elt = (Element) value;

                            if (elt.getTagName().equalsIgnoreCase("state")) {
                                String name = elt.getAttribute("name");
                                //String lastName = elt.getAttribute("lastName");
                                if (aut instanceof Finite_Automation || aut instanceof Millie_automation || aut instanceof Shop_automation) {
                                    return name;
                                } else if (aut instanceof Mour_automation) {
                                    return name + "," + elt.getAttribute("lambda");
                                }

                            }

                        }
                    }

                    return super.convertValueToString(cell);
                }

                // Overrides method to store a cell label in the model
                public void cellLabelChanged(Object cell, Object newValue,
                                             boolean autoSize) {
                    if (cell instanceof mxCell && newValue != null) {
                        Object value = ((mxCell) cell).getValue();

                        if (value instanceof Node) {
                            String name = newValue.toString();
                            Element elt = (Element) value;

                            if (elt.getTagName().equalsIgnoreCase("state")) {

                                elt = (Element) elt.cloneNode(true);

                                elt.setAttribute("name", name);


                                newValue = elt;
                            }


                        }
                    }

                    super.cellLabelChanged(cell, newValue, autoSize);
                }
            };

            // Overrides method to create the editing value
            graphComponent = new mxGraphComponent(graph) {
                /**
                 *
                 */


                public String getEditingValue(Object cell, EventObject trigger) {
                    if (cell instanceof mxCell) {
                        Object value = ((mxCell) cell).getValue();

                        if (value instanceof Element) {
                            Element elt = (Element) value;

                            if (elt.getTagName().equalsIgnoreCase("state")) {
                                String name = elt.getAttribute("name");


                                return name;
                            }

                        }
                    }

                    return super.getEditingValue(cell, trigger);
                }

                ;

            };


            parent = graph.getDefaultParent();

            graph.getModel().beginUpdate();

            try {
                int n = aut.getAlphabet_size();
                int m = aut.getStates_count();
                String[] alphabet = aut.getAlphabet();
                String[][] output_labels = new String[n][m];
                int[][] states = aut.getJump_table();
                State[] States = aut.getStates();
                String[] lambda = null;
                HashMap<Key, Takt> rules_map = new HashMap<Key, Takt>();
                if (aut instanceof Mour_automation) {
                    lambda = ((Mour_automation) aut).getLambda();
                }
                if (aut instanceof Millie_automation) {
                    output_labels = ((Millie_automation) aut).getLambda();
                }
                if (aut instanceof Shop_automation) {
                    rules_map = ((Shop_automation) aut).getRule_map();
                }
                vert = new mxCell[m];
                int r = 150;

                mxStylesheet stylesheet = graph.getStylesheet();
                Hashtable<String, Object> style = new Hashtable<String, Object>();
                style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);

                style.put(mxConstants.STYLE_FONTCOLOR, "#0D142B");
                style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_SIDETOSIDE);
                style.put(mxConstants.PERIMETER_ELLIPSE, true);
                style.put(mxConstants.STYLE_STROKEWIDTH, 2);
                style.put(mxConstants.STYLE_ARCSIZE, 50);
                style.put(mxConstants.STYLE_FONTSIZE, 18);
                style.put(mxConstants.STYLE_STROKECOLOR, "#0D142B");


                stylesheet.putCellStyle("ROUNDED", style);

                Hashtable<String, Object> style2 = new Hashtable<String, Object>();

                style2.put(mxConstants.STYLE_FONTCOLOR, "#251818");
                style2.put(mxConstants.STYLE_FONTSIZE, 18);

                style2.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_SIDETOSIDE);
                style2.put(mxConstants.STYLE_ROUNDED, true);
                style2.put(mxConstants.PERIMETER_ELLIPSE, true);
                style2.put(mxConstants.STYLE_STROKEWIDTH, 2);
                style2.put(mxConstants.STYLE_STROKECOLOR, "#414F81");
                style2.put(mxConstants.STYLE_ARCSIZE, 50);
                style2.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ELBOW);//] = mxEdgeStyle.ElbowConnector;
                stylesheet.putCellStyle("edge_style", style2);


                for (int i = 0; i < m; i++) {
                    Element state = xmlDocument.createElement("state");
                    state.setAttribute("id", States[i].getId());
                    state.setAttribute("name", States[i].getName());
                    state.setAttribute("x", States[i].getX() + "");
                    state.setAttribute("y", States[i].getY() + "");
                    if (aut instanceof Mour_automation) {
                        state.setAttribute("lambda", lambda[i]);
                    }
                    System.out.println(States[i].getX() + " " + States[i].getName());
                    vert[i] = (mxCell) graph.insertVertex(parent, String.valueOf(i), state, States[i].getX(), States[i].getY(), 50, 50, "ROUNDED");//insertVertex(parent, null, sourceNode, 20, 20,
                }
                if (aut instanceof Shop_automation) {

                    Iterator<HashMap.Entry<Key, Takt>> itr = rules_map.entrySet().iterator();
                    while (itr.hasNext()) {
                        Map.Entry<Key, Takt> me = (Map.Entry) itr.next();
                        System.out.println(me.getValue().getId_state());
                        graph.insertEdge(parent, "id", me.getKey().curr_symb_lent + "," + me.getKey().curr_symb_stek + "," + me.getValue().getStek(),
                                vert[((Shop_automation) aut).getIndexOf(me.getKey().id_state)],
                                vert[((Shop_automation) aut).getIndexOf(me.getValue().getId_state())],
                                "edge_style");
                    }
                } else if (aut instanceof Millie_automation) {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            System.out.print(states[i][j]);
                            if (states[i][j] != -1 && states[i][j] < m) {
                                System.out.println("from " + j + " to " + states[i][j] + "read " + alphabet[i]);
                                graph.insertEdge(parent, i + "+" + j, alphabet[i] + "," + output_labels[i][j],
                                        vert[j],
                                        vert[states[i][j]],
                                        "edge_style");
                            }

                        }
                    }
                } else {
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < m; j++) {
                            System.out.print(states[i][j]);
                            if (states[i][j] != -1 && states[i][j] < m) {
                                System.out.println("from " + j + " to " + states[i][j] + "read " + alphabet[i]);
                                graph.insertEdge(parent, i + "+" + j, alphabet[i],
                                        vert[j],
                                        vert[states[i][j]],
                                        "edge_style");
                            }

                        }
                    }
                }
                //Object e4 = graph.insertEdge(parent, null, "", v1, v4);
            } finally {
                graph.getModel().endUpdate();
            }


            graph.setMultigraph(false);
            graphComponent.setEnterStopsCellEditing(true);

            graph.setAllowDanglingEdges(false);
            graph.setDisconnectOnMove(false);
            graph.setEdgeLabelsMovable(true);
            graphComponent.setConnectable(true);
            graphComponent.setToolTips(true);

            mxParallelEdgeLayout layout2 = new mxParallelEdgeLayout(graph);
            mxCircleLayout layout = new mxCircleLayout(graph);
            layout.setRadius(200);
            layout.setX0(100);
            layout.setY0(100);
            layout.execute(parent);
            layout2.execute(parent);





            // Enables rubberband selection
            new mxRubberband(graphComponent);
            new mxKeyboardHandler(graphComponent);

            // Installs automatic validation (use editor.validation = true
            // if you are using an mxEditor instance)
            graph.getModel().addListener(mxEvent.CHANGE, new mxEventSource.mxIEventListener() {
                public void invoke(Object sender, mxEventObject evt) {

                    graphComponent.validateGraph();
                }
            });
            /*
            graph.getModel().addListener(mxEvent.CHANGE, new mxIEventListener(){
                public void invoke(Object sender, mxEventObject evt) {
                    if (graph != null) {
                        graph.getModel().beginUpdate();
                        evt.consume();
                        try {

                           Map<String,Object> changes = evt.getProperties();//.changes;
                            for (int i = 0; i < changes.size(); i++) {

                                Object state = graph.getView().getState(changes.);
                                if (state != null) {//color #1C86EE means new insert
                                    if (state.style[mxConstants.STYLE_IMAGE_BACKGROUND] != "#1C86EE"
                                            && state.style[mxConstants.STYLE_STROKECOLOR] != "#1C86EE"
                                            && state.style[mxConstants.STYLE_FONTCOLOR] != "#1C86EE") {
                                        graph.setCellStyles(mxConstants.STYLE_IMAGE_BACKGROUND, '#68228B',[change.cell]);
                                        graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, '#68228B',[change.cell]);
                                    }
                                }
                            }
                        } finally {
                            graph.getModel().endUpdate();
                            graph.refresh();
                        }
                    }
                }
            });
    */
            // Initial validation
            graphComponent.validateGraph();

            getContentPane().add(graphComponent);


        }

        /**
         *
         */

        public Element getElfromObj(Object source) {
            if (source instanceof mxCell) {
                Object value = ((mxCell) source).getValue();

                if (value instanceof Element) {
                    Element elt = (Element) value;


                    return elt;

                }
            }
            return null;
        }

        public void Show_vert(String id, String label) {
            graph.getModel().beginUpdate();
            try {
                mxStylesheet stylesheet = graph.getStylesheet();
                Hashtable<String, Object> style = new Hashtable<String, Object>();
                style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);

                style.put(mxConstants.STYLE_FONTCOLOR, "#0D142B");

                style.put(mxConstants.PERIMETER_ELLIPSE, true);
                style.put(mxConstants.STYLE_STROKEWIDTH, 5);
                style.put(mxConstants.STYLE_ARCSIZE, 50);
                style.put(mxConstants.STYLE_FONTSIZE, 20);
                style.put(mxConstants.STYLE_STROKECOLOR, "#0D142B");

                stylesheet.putCellStyle("ROUND", style);


                Hashtable<String, Object> style2 = new Hashtable<String, Object>();

                style2.put(mxConstants.STYLE_FONTCOLOR, "#251818");
                style2.put(mxConstants.STYLE_FONTSIZE, 20);


                style2.put(mxConstants.PERIMETER_ELLIPSE, true);
                style2.put(mxConstants.STYLE_STROKEWIDTH, 4);
                style2.put(mxConstants.STYLE_STROKECOLOR, "#414F00");

                //] = mxEdgeStyle.ElbowConnector;
                stylesheet.putCellStyle("edge_show", style2);
                Object[] hightlight = new Object[1];
                Object[] highlight_edge = new Object[1];


                //Object[] connect = graph.getChildCells(parent);
                Object[] connect = graph.getChildVertices(parent);
                Object[] edges = graph.getAllEdges(graph.getChildCells(parent));
                graph.setCellStyle("ROUNDED", connect);
                graph.setCellStyle("edge_style", edges);
                for (int j = 0; j < connect.length; j++) {


                    if (connect[j] instanceof mxCell) {
                        //((mxCell) connect[j]).setStyle("ROUND");
                        Object value = ((mxCell) connect[j]).getValue();

                        if (value instanceof Element) {
                            Element elt = (Element) value;


                            String id_temp = elt.getAttribute("id");
                            System.out.println("id=" + id_temp + " quals " + id);
                            if (id_temp.equals(id)) {

                                hightlight[0] = (connect[j]);

                                System.out.println("edges= " + ((mxCell) connect[j]).getEdgeCount());

                                for (int i = 0; i < edges.length; i++) {
                                    Element edge = (Element) ((mxCell) edges[i]).getSource().getValue();
                                    System.out.println("i=" + i + "id=" + edge.getAttribute("id"));
                                    // if(graph.getLabel(((mxCell)connect[j]).getEdgeAt(i)).equals(label)){
                                    //.getEdgeAt(i).setStyle("edge_show");
                                    // }
                               /*     if(((mxCell)edge[i]).getSource() != null) {
                                        System.out.println(((mxCell)edge[i]).toString());
                                        if (((mxCell) edge[i]).getSource().equals((mxCell) connect[j]) && graph.getLabel(((mxCell) edge[i])).equals(label)) {
    */
                                    if (edge.getAttribute("id").equals(id) && graph.getLabel((mxCell) edges[i]).equals(label)) {
                                        System.out.println("i=" + i);
                                        highlight_edge[0] = edges[i];
                                    }

                                }

                                //ellStyle("ROUND",hightlight);
                                graph.setCellStyle("ROUND", hightlight);
                                try {
                                    graph.setCellStyle("edge_show", highlight_edge);
                                } catch (NullPointerException e) {

                                }
                            }
                        }
                    }
                }

            } finally {
                // Updates the display
                graph.getModel().endUpdate();
                (graphComponent).validateGraph();
            }
        }

        private int getIdStr(String[] alph, String str) {
            for (int i = 0; i < alph.length; i++) {
                if (alph[i] != null) {
                    if (alph[i].equals(str)) {
                        return i;
                    }
                }
            }
            return -1;
        }

        public Automation SaveGraph(Shop_automation automat) {
            graph.getModel().beginUpdate();

            try {
                System.out.println("SaveGraph");
                Object[] cells = graph.getChildCells(parent);
                HashMap<Key, Takt> map = new HashMap<Key, Takt>();

                int num_of_states = graph.getChildVertices(parent).length;
                int alphabet_size = cells.length - num_of_states;

                ArrayList<String> alfabet_list = new ArrayList();

                ArrayList<State> states = new ArrayList();

                int count_s = 0;
                int count_alp = 0;

                for (int i = 0; i < cells.length; i++) {

                    if (cells[i] instanceof mxCell) {
                        //Object el_val = ((mxCell) cells[i]).getValue();
                        Element elem = getElfromObj(cells[i]);
                        //                    System.out.print(elem.toString());
                        if (((mxCell) cells[i]).isEdge()) {
                            System.out.println("edge");


                            String[] symbols = graph.getLabel((mxCell) cells[i]).split(",");
                            if (symbols[0].equals("") || symbols[1].equals("")) {
                                System.out.print("label=0");
                                Object source = ((mxCell) cells[i]).getSource();
                                if (getElfromObj(source) != null) {
                                    Element elt = getElfromObj(source);
                                    String id = elt.getAttribute("id");
                                    String name = elt.getAttribute("name");
                                    System.out.println("from id=" + id + " name=" + name);

                                }
                                Object target = ((mxCell) cells[i]).getTarget();
                                if (getElfromObj(target) != null) {
                                    Element elt = getElfromObj(target);
                                    String id = elt.getAttribute("id");
                                    String name = elt.getAttribute("name");
                                    System.out.println("to id=" + id + " name=" + name);

                                }

                            } else {
                                int index_str = -1;

                                if (alfabet_list.contains(symbols[0])) {
                                    index_str = alfabet_list.indexOf(symbols[0]);
                                } else {
                                    alfabet_list.add(symbols[0]);
                                    count_alp++;
                                }
                                Object source = ((mxCell) cells[i]).getSource();
                                Object target = ((mxCell) cells[i]).getTarget();
                                if (getElfromObj(source) != null && getElfromObj(target) != null) {
                                    Element elt = getElfromObj(source);
                                    String id = elt.getAttribute("id");
                                    System.out.println("id= " + id);
                                    Element eltT = getElfromObj(target);
                                    String idT = eltT.getAttribute("id");
                                    System.out.println("id= " + idT + index_str);

                                    map.put(new Key(id, symbols[0], symbols[1]), new Takt(idT, symbols[2]));

                                } else {

                                    System.out.println("error no source or target" + graph.getLabel((mxCell) cells[i]));
                                }


                            }
                        } else if (((mxCell) cells[i]).isVertex()) {
                            elem.setAttribute("x", String.valueOf(((int) ((mxCell) cells[i]).getGeometry().getPoint().getLocation().getX())));
                            elem.setAttribute("y", String.valueOf(((int) ((mxCell) cells[i]).getGeometry().getPoint().getLocation().getY())));
                            System.out.println("vertex:" + elem.getAttribute("id") + " " + elem.getAttribute("name"));

                            states.add(new State(elem.getAttribute("id"),
                                    elem.getAttribute("name"),
                                    Integer.parseInt(elem.getAttribute("x")),
                                    Integer.parseInt(elem.getAttribute("y"))));
                        }


                    }
                }
                alphabet_size = alfabet_list.size();
                num_of_states = states.size();


                automat.setAlphabet(alfabet_list.toArray(new String[alphabet_size]));
                automat.setStates(states);
                automat.setAlphabet_size(alphabet_size);
                automat.setStates_count(num_of_states);

                return automat;

            } finally {
                graph.getModel().endUpdate();
                (graphComponent).validateGraph();


            }
        }
        public Automation SaveGraph(Automation automat) {


            graph.getModel().beginUpdate();

            try {
                System.out.println("SaveGraph");
                Object[] cells = graph.getChildCells(parent);
                int[][] jump_table;
                String[] lambda;
                int num_of_states = graph.getChildVertices(parent).length;
                int alphabet_size = cells.length - num_of_states;
                lambda = new String[num_of_states];
                ArrayList alfabet_list = new ArrayList();

                State[] states = new State[num_of_states];
                jump_table = new int[alphabet_size][num_of_states];
                for (int i = 0; i < alphabet_size; i++) {
                    for (int j = 0; j < num_of_states; j++) {
                        jump_table[i][j] = -1;
                    }
                }
                int count_s = 0;
                int count_alp = 0;

                for (int i = 0; i < cells.length; i++) {

                    if (cells[i] instanceof mxCell) {
                        //Object el_val = ((mxCell) cells[i]).getValue();
                        Element elem = getElfromObj(cells[i]);
                        //                    System.out.print(elem.toString());
                        if (((mxCell) cells[i]).isEdge()) {
                            System.out.println("edge");
                            if (automat instanceof Millie_automation) {
                                String[][] output_table = new String[alphabet_size][num_of_states];
                                String[] symbols = graph.getLabel((mxCell) cells[i]).split(",");
                                if (symbols[0].equals("") || symbols[1].equals("")) {
                                    System.out.print("label=0");
                                    Object source = ((mxCell) cells[i]).getSource();
                                    if (getElfromObj(source) != null) {
                                        Element elt = getElfromObj(source);
                                        String id = elt.getAttribute("id");
                                        String name = elt.getAttribute("name");
                                        System.out.println("from id=" + id + " name=" + name);

                                    }
                                    Object target = ((mxCell) cells[i]).getTarget();
                                    if (getElfromObj(target) != null) {
                                        Element elt = getElfromObj(target);
                                        String id = elt.getAttribute("id");
                                        String name = elt.getAttribute("name");
                                        System.out.println("to id=" + id + " name=" + name);

                                    }

                                } else {
                                    int index_str = -1;

                                    if (alfabet_list.contains(symbols[0])) {
                                        index_str = alfabet_list.indexOf(symbols[0]);
                                    } else {
                                        alfabet_list.add(symbols[0]);
                                        count_alp++;
                                    }
                                    Object source = ((mxCell) cells[i]).getSource();
                                    Object target = ((mxCell) cells[i]).getTarget();
                                    if (getElfromObj(source) != null && getElfromObj(target) != null) {
                                        Element elt = getElfromObj(source);
                                        String id = elt.getAttribute("id");
                                        System.out.println("id= " + id);
                                        Element eltT = getElfromObj(target);
                                        String idT = eltT.getAttribute("id");
                                        System.out.println("id= " + idT + index_str);

                                        if (index_str == -1) {
                                            // System.out.println(count_alp + " "+ alfabet_list.get(count_alp));

                                            jump_table[count_alp - 1][Integer.parseInt(id)] = Integer.parseInt(idT);
                                            output_table[count_alp - 1][Integer.parseInt(id)] = symbols[1];
                                            //System.out.println("from "+ id +" to "+ idT + " " + alfabet_list.get(count_alp));
                                        } else {
                                            jump_table[index_str][Integer.parseInt(id)] = Integer.parseInt(idT);
                                            output_table[index_str][Integer.parseInt(id)] = symbols[1];
                                            // System.out.println("from "+ id +" to "+ idT + " " + alfabet_list.get(index_str));
                                        }

                                    } else {

                                        System.out.println("error no source or target" + graph.getLabel((mxCell) cells[i]));
                                    }


                                }
                            } else {
                                //System.out.print("lab= "+elem.getAttribute("label") );
                                if (graph.getLabel((mxCell) cells[i]).equals("")) {
                                    System.out.print("label=0");
                                    Object source = ((mxCell) cells[i]).getSource();
                                    if (getElfromObj(source) != null) {
                                        Element elt = getElfromObj(source);
                                        String id = elt.getAttribute("id");
                                        String name = elt.getAttribute("name");
                                        System.out.println("from id=" + id + " name=" + name);

                                    }
                                    Object target = ((mxCell) cells[i]).getTarget();
                                    if (getElfromObj(target) != null) {
                                        Element elt = getElfromObj(target);
                                        String id = elt.getAttribute("id");
                                        String name = elt.getAttribute("name");
                                        System.out.println("to id=" + id + " name=" + name);

                                    }

                                } else {
                                    int index_str = -1;

                                    if (alfabet_list.contains(graph.getLabel((mxCell) cells[i]))) {
                                        index_str = alfabet_list.indexOf(graph.getLabel((mxCell) cells[i]));
                                    } else {
                                        alfabet_list.add(graph.getLabel((mxCell) cells[i]));
                                        count_alp++;
                                    }
                                    Object source = ((mxCell) cells[i]).getSource();
                                    Object target = ((mxCell) cells[i]).getTarget();
                                    if (getElfromObj(source) != null && getElfromObj(target) != null) {
                                        Element elt = getElfromObj(source);
                                        String id = elt.getAttribute("id");
                                        System.out.println("id= " + id);
                                        Element eltT = getElfromObj(target);
                                        String idT = eltT.getAttribute("id");
                                        System.out.println("id= " + idT + index_str);

                                        if (index_str == -1) {
                                            // System.out.println(count_alp + " "+ alfabet_list.get(count_alp));

                                            jump_table[count_alp - 1][Integer.parseInt(id)] = Integer.parseInt(idT);
                                            //System.out.println("from "+ id +" to "+ idT + " " + alfabet_list.get(count_alp));
                                        } else {
                                            jump_table[index_str][Integer.parseInt(id)] = Integer.parseInt(idT);
                                            // System.out.println("from "+ id +" to "+ idT + " " + alfabet_list.get(index_str));
                                        }

                                    } else {

                                        System.out.println("error no source or target" + graph.getLabel((mxCell) cells[i]));
                                    }


                                }
                            }
                        } else if (((mxCell) cells[i]).isVertex()) {
                            elem.setAttribute("x", String.valueOf(((int) ((mxCell) cells[i]).getGeometry().getPoint().getLocation().getX())));
                            elem.setAttribute("y", String.valueOf(((int) ((mxCell) cells[i]).getGeometry().getPoint().getLocation().getY())));
                            System.out.println("vertex:" + elem.getAttribute("id") + " " + elem.getAttribute("name"));
                            if (automat instanceof Mour_automation) {
                                states[count_s] = (new State(elem.getAttribute("id"),
                                        elem.getAttribute("name"),
                                        Integer.parseInt(elem.getAttribute("x")),
                                        Integer.parseInt(elem.getAttribute("y")),
                                        elem.getAttribute("lambda")));
                                lambda[count_s] = elem.getAttribute("lambda");
                            } else if (automat instanceof Finite_Automation || automat instanceof Millie_automation) {
                                states[count_s] = (new State(elem.getAttribute("id"),
                                        elem.getAttribute("name"),
                                        Integer.parseInt(elem.getAttribute("x")),
                                        Integer.parseInt(elem.getAttribute("y"))));
                            }
                            count_s++;
                        }
                    }
                }
                alphabet_size = alfabet_list.size();
                num_of_states = count_s;
                String[] alfabet = new String[alphabet_size];
                System.out.println("Alfabet");
                for (int i = 0; i < alfabet_list.size(); i++) {
                    alfabet[i] = (String) alfabet_list.get(i);
                    System.out.print(alfabet[i] + " ");
                }
                System.out.println();
                System.out.println("jump_table");
                for (int j = 0; j < num_of_states; j++) {
                    for (int i = 0; i < alphabet_size; i++) {

                        System.out.print(jump_table[i][j] + " ");// = -1;
                    }
                    System.out.println();
                }
                automat.setJump_table(jump_table);
                automat.setAlphabet(alfabet);
                automat.setStates(states);
                automat.setAlphabet_size(alphabet_size);
                automat.setStates_count(num_of_states);

                return automat;

            } finally {
                graph.getModel().endUpdate();
                (graphComponent).validateGraph();


            }

        }
    }
}
