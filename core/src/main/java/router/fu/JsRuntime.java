package router.fu;

import jsinterop.annotations.JsFunction;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;
import jsinterop.base.JsArrayLike;

final class JsRuntime
{
  private JsRuntime()
  {
  }

  @JsProperty( namespace = JsPackage.GLOBAL, name = "window" )
  static native Window window();

  @FunctionalInterface
  @JsFunction
  interface EventListener
  {
    void handleEvent( Object event );
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Document" )
  static final class Document
  {
    private Document()
    {
    }

    @JsProperty( name = "title" )
    native String title();
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "History" )
  static final class History
  {
    private History()
    {
    }

    native void replaceState( Object data, String unused, String url );
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Location" )
  static final class Location
  {
    private Location()
    {
    }

    @JsProperty( name = "hash" )
    native String hash();

    @JsProperty( name = "hash" )
    native void setHash( String hash );

    @JsProperty( name = "pathname" )
    native String pathname();

    @JsProperty( name = "search" )
    native String search();
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "RegExp" )
  static final class RegExp
  {
    RegExp( String pattern )
    {
    }

    native RegExpResult exec( String text );
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Array" )
  static final class RegExpResult
    implements JsArrayLike<String>
  {
    private RegExpResult()
    {
    }
  }

  @JsType( isNative = true, namespace = JsPackage.GLOBAL, name = "Window" )
  static final class Window
  {
    private Window()
    {
    }

    native void addEventListener( String type, EventListener listener, boolean useCapture );

    @JsProperty( name = "document" )
    native Document document();

    @JsProperty( name = "history" )
    native History history();

    @JsProperty( name = "location" )
    native Location location();

    native void removeEventListener( String type, EventListener listener, boolean useCapture );
  }
}
