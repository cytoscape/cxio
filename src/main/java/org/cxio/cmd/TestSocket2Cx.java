package org.cxio.cmd;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

//http://www.javaworld.com/article/2853780/java-app-dev/socket-programming-for-scalable-systems.html
public class TestSocket2Cx {
    public static void main(final String[] args) throws IOException {
        final String host_name = "www.google.com";
        final int port_number = 80;
        try {
            final Socket clientSocket = new Socket(host_name, port_number);
            final InputStream is = clientSocket.getInputStream();
            final PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
            pw.println("GET / HTTP/1.0");
            pw.println();
            pw.flush();
            final byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) != -1) {
                final String output = new String(buffer, 0, read);
                System.out.print(output);
                System.out.flush();
            }
            clientSocket.close();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static String readIt(final InputStream is, final int bufferSize) {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            while (true) {
                final int i = in.read(buffer, 0, buffer.length);
                System.out.println(i);
                if (i < 0) {
                    break;
                }
                out.append(buffer, 0, i);
            }
        }
        catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }
}
