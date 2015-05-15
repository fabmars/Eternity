package fr.esiea.glpoo.eternity.io;

import java.net.MalformedURLException;
import java.net.URL;

public class Utils {

  public static String getFileName(URL url) {
    String path = url.getPath();
    return path.substring(path.lastIndexOf("/")+1);
  }

  public static URL resolveSibling(URL url, String otherFileName) throws MalformedURLException {
    String parent = getParentString(url);
    return new URL(parent + otherFileName);
  }
  
  public static URL getParent(URL url) throws MalformedURLException {
    String parent = getParentString(url);
    return new URL(parent);
  }

  private static String getParentString(URL url) {
    String urlString = url.toExternalForm();
    int endIndex = urlString.lastIndexOf("/")+1;
    String beginning = urlString.substring(0, endIndex);
    return beginning;
  }
}
