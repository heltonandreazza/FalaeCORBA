package Corba;


/**
* Corba/CORBA_FalaeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CORBA_module.idl
* S�bado, 28 de Maio de 2016 21h38min00s BRT
*/

abstract public class CORBA_FalaeHelper
{
  private static String  _id = "IDL:Corba/CORBA_Falae:1.0";

  public static void insert (org.omg.CORBA.Any a, Corba.CORBA_Falae that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Corba.CORBA_Falae extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (Corba.CORBA_FalaeHelper.id (), "CORBA_Falae");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Corba.CORBA_Falae read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_CORBA_FalaeStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Corba.CORBA_Falae value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static Corba.CORBA_Falae narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Corba.CORBA_Falae)
      return (Corba.CORBA_Falae)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      Corba._CORBA_FalaeStub stub = new Corba._CORBA_FalaeStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static Corba.CORBA_Falae unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Corba.CORBA_Falae)
      return (Corba.CORBA_Falae)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      Corba._CORBA_FalaeStub stub = new Corba._CORBA_FalaeStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
