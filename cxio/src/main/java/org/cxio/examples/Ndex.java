package org.cxio.examples;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.cxio.aspects.readers.CartesianLayoutFragmentReader;
import org.cxio.aspects.readers.EdgeAttributesFragmentReader;
import org.cxio.aspects.readers.EdgesFragmentReader;
import org.cxio.aspects.readers.NodeAttributesFragmentReader;
import org.cxio.aspects.readers.NodesFragmentReader;
import org.cxio.core.CxReader;
import org.cxio.core.CxWriter;
import org.cxio.core.interfaces.AspectElement;
import org.cxio.examples.Citation.CitationElement;
import org.cxio.examples.Citation.CitationFragmentReader;
import org.cxio.examples.Citation.CitationFragmentWriter;
import org.ndexbio.model.object.NdexPropertyValuePair;
import org.ndexbio.model.object.SimplePropertyValuePair;
import org.ndexbio.model.object.network.Citation;

public class Ndex {
    
    public static void main(final String[] args) throws IOException {
        
        
        Citation cit = new  Citation();
        
        
        List<String> cont = new ArrayList<String>();
        cont.add("Cao, R.");
        cont.add("Bhattacharya, D.");
        cont.add("Cheng, J.");
        cit.setContributors(cont);
        cit.setId(93928L);
        cit.setIdentifier("doi:403i3o");
        cit.setIdType("doi");
        
        SimplePropertyValuePair p0 = new  SimplePropertyValuePair();
        p0.setName("A");
        p0.setType("Atype");
        p0.setValue("a");
        SimplePropertyValuePair p1 = new  SimplePropertyValuePair();
        p1.setName("B");
        p1.setType("Btype");
        p1.setValue("b");
        List<SimplePropertyValuePair> p = new ArrayList<SimplePropertyValuePair>();
        p.add(p0);
        p.add(p1);
        cit.setPresentationProperties(p);
        cit.setTitle("Large-scale model quality assessment for improving protein tertiary structure prediction");
        cit.setType("journal");
         
        List<NdexPropertyValuePair> ps = new ArrayList<NdexPropertyValuePair>();
        NdexPropertyValuePair ps0 = new NdexPropertyValuePair();
        ps0.setDataType("datatype is string");
        ps0.setPredicateId(393l);
        ps0.setPredicateString("ps");
        ps0.setType("type is string");
        ps0.setValue("value is aa");
        ps0.setValueId(1L);
        
        NdexPropertyValuePair ps1 = new NdexPropertyValuePair();
        ps1.setDataType("datatype is string");
        ps1.setPredicateId(394l);
        ps1.setPredicateString("ps");
        ps1.setType("type is string");
        ps1.setValue("value is bb");
        ps1.setValueId(2L);
        ps.add(ps0);
        ps.add(ps1);
        cit.setProperties(ps);
        
        final List<AspectElement> elements = new ArrayList<AspectElement>();
        elements.add(CitationElement.createInstance(cit));
        
        // Writing to CX
        // -------------
        final OutputStream out = new ByteArrayOutputStream();

        final CxWriter w = CxWriter.createInstance(out, true);

        w.addAspectFragmentWriter(CitationFragmentWriter.createInstance());
       
        w.start();
        w.write(elements );
        w.end();

        final String cx_json_str = out.toString();
        System.out.println(cx_json_str);

        // Reading from CX
        // ---------------
        final CxReader r = CxReader.createInstance(cx_json_str);
        r.addAspectFragmentReader(CitationFragmentReader.createInstance());
      
        r.reset();
        while (r.hasNext()) {
            final List<AspectElement> elements2 = r.getNext();
            if (!elements2.isEmpty()) {
                final String aspect_name = elements2.get(0).getAspectName();
                System.out.println();
                System.out.println(aspect_name + ": ");
                for (final AspectElement element : elements2) {
                    System.out.println(element.toString());
                }
            }
        }
        
    }

}
