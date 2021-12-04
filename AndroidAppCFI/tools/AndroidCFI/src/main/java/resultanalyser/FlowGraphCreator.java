//package resultanalyser;
//
//import org.apache.batik.transcoder.TranscoderException;
//import org.apache.batik.transcoder.TranscoderInput;
//import org.apache.batik.transcoder.TranscoderOutput;
//import org.apache.batik.transcoder.image.JPEGTranscoder;
//import org.jgrapht.Graph;
//import org.jgrapht.graph.DefaultDirectedGraph;
//import org.jgrapht.graph.DefaultEdge;
//import org.jgrapht.graph.SimpleGraph;
//import org.jgrapht.nio.*;
//import org.jgrapht.nio.dot.*;
//import org.jgrapht.traverse.DepthFirstIterator;
//import util.PathManager;
//import util.RuntimeExecutor;
//
//import java.io.*;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.nio.charset.StandardCharsets;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//
///**
// * A simple introduction to using JGraphT.
// *
// * @author Barak Naveh
// */
//public final class FlowGraphCreator
//{
//    private FlowGraphCreator()
//    {
//    } // ensure non-instantiability.
//
//    /**
//     * The starting point for the demo.
//     *
//     * @param args ignored.
//     *
//     * @throws URISyntaxException if invalid URI is constructed.
//     * @throws ExportException if graph cannot be exported.
//     */
//    public static void main(String[] args)
//            throws URISyntaxException,
//            ExportException
//    {
//
//        Graph<String, DefaultEdge> stringGraph = createStringGraph();
//
//        // note undirected edges are printed as: {<v1>,<v2>}
//        System.out.println("-- toString output");
//        System.out.println(stringGraph.toString());
//        System.out.println();
//
//
//        // create a graph based on URI objects
//        Graph<URI, DefaultEdge> hrefGraph = createHrefGraph();
//
//        // find the vertex corresponding to www.jgrapht.org
//        URI start = hrefGraph
//                .vertexSet().stream().filter(uri -> uri.getHost().equals("www.jgrapht.org")).findAny()
//                .get();
//
//
//        // perform a graph traversal starting from that vertex
//        System.out.println("-- traverseHrefGraph output");
//        traverseHrefGraph(hrefGraph, start);
//        System.out.println();
//
//        System.out.println("-- renderHrefGraph output");
//        renderHrefGraph(hrefGraph);
//        System.out.println();
//    }
//
//    /**
//     * Creates a toy directed graph based on URI objects that represents link structure.
//     *
//     * @return a graph based on URI objects.
//     */
//    private static Graph<URI, DefaultEdge> createHrefGraph()
//            throws URISyntaxException
//    {
//
//        Graph<URI, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);
//
//        URI google = new URI("http://www.google.com");
//        URI wikipedia = new URI("http://www.wikipedia.org");
//        URI jgrapht = new URI("http://www.jgrapht.org");
//
//        // add the vertices
//        g.addVertex(google);
//        g.addVertex(google);
//        g.addVertex(wikipedia);
//        g.addVertex(jgrapht);
//
//        // add edges to create linking structure
//        g.addEdge(jgrapht, wikipedia);
//        g.addEdge(google, jgrapht);
//        g.addEdge(google, wikipedia);
//        g.addEdge(wikipedia, google);
//
//
//        return g;
//    }
//
//    /**
//     * Traverse a graph in depth-first order and print the vertices.
//     *
//     * @param hrefGraph a graph based on URI objects
//     *
//     * @param start the vertex where the traversal should start
//     */
//    private static void traverseHrefGraph(Graph<URI, DefaultEdge> hrefGraph, URI start)
//    {
//        Iterator<URI> iterator = new DepthFirstIterator<>(hrefGraph, start);
//        while (iterator.hasNext()) {
//            URI uri = iterator.next();
//            System.out.println(uri);
//        }
//    }
//
//    /**
//     * Render a graph in DOT format.
//     *
//     * @param hrefGraph a graph based on URI objects
//     */
//    private static void renderHrefGraph(Graph<URI, DefaultEdge> hrefGraph)
//            throws ExportException
//    {
//
//        DOTExporter<URI, DefaultEdge> exporter =
//                new DOTExporter<>(v -> v.getHost().replace('.', '_'));
//        exporter.setVertexAttributeProvider((v) -> {
//            Map<String, Attribute> map = new LinkedHashMap<>();
//            map.put("label", DefaultAttribute.createAttribute(v.toString()));
//            return map;
//        });
//        Writer writer = new StringWriter();
//        exporter.exportGraph(hrefGraph, writer);
//        try {
////            String cmd = "echo '" + writer.toString().replace("\"", "\\\"") + "' | dot -Tsvg > /home/ishadi/Desktop/output2.svg";
////            String cmd = "echo 'digraph { a -> b }' | dot -Tsvg > "+PathManager.getGraphFolderPath()+"output2.svg";
//            String outFile = PathManager.getConfigFolderPath()+"output7.svg";
//            String cmd = "dot -Tsvg "+PathManager.getPredictionGraphFilePath()+" -o"+ outFile ;
////            String cmd = "echo 'digraph { a -> b }' | dot -Tsvg -o"+ PathManager.getConfigFolderPath()+"output2.svg" ;
//            RuntimeExecutor.runCommand(cmd, false);
////            convertToJPEG(PathManager.getConfigFolderPath()+"output4.svg");
//            RuntimeExecutor.showImage(outFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(writer.toString());
//    }
//
//    /**
//     * Create a toy graph based on String objects.
//     *
//     * @return a graph based on String objects.
//     */
//    private static Graph<String, DefaultEdge> createStringGraph()
//    {
//        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
//
//        String v1 = "v1";
//        String v2 = "v2";
//        String v3 = "v3";
//        String v4 = "v4";
//
//        // add the vertices
//        g.addVertex(v1);
//        g.addVertex(v2);
//        g.addVertex(v3);
//        g.addVertex(v4);
//        g.addVertex(v4);
//        g.addVertex(v4);
//
//        // add edges to create a circuit
//        g.addEdge(v1, v2);
//        g.addEdge(v2, v3);
//        g.addEdge(v3, v4);
//        g.addEdge(v4, v1);
//
//        return g;
//    }
//
//    static void convertToJPEG(String svgImage) throws IOException, TranscoderException {
//
//
//            // Create a JPEG transcoder
//            JPEGTranscoder t = new JPEGTranscoder();
//
//            // Set the transcoding hints.
//            t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
//                    new Float(.8));
//
//            // Create the transcoder input.
//            String svgURI = new File(svgImage).toURL().toString();
//            TranscoderInput input = new TranscoderInput(svgURI);
//
//            // Create the transcoder output.
//            OutputStream ostream = new FileOutputStream(svgImage.replace("svg", "jpg"));
//            TranscoderOutput output = new TranscoderOutput(ostream);
//
//            // Save the image.
//            t.transcode(input, output);
//
//            // Flush and close the stream.
//            ostream.flush();
//            ostream.close();
//
//        }
//}