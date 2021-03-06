package Group;


/**
* Group/CRUD_FalaePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Group_Module.idl
* Ter�a-feira, 3 de Maio de 2016 21h51min17s BRT
*/

public abstract class CRUD_FalaePOA extends org.omg.PortableServer.Servant
 implements Group.CRUD_FalaeOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("inserir_grupo", new java.lang.Integer (0));
    _methods.put ("createGroup", new java.lang.Integer (1));
    _methods.put ("getGroups", new java.lang.Integer (2));
    _methods.put ("removeGroup", new java.lang.Integer (3));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // Group/CRUD_Falae/inserir_grupo
       {
         String $result = null;
         $result = this.inserir_grupo ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // Group/CRUD_Falae/createGroup
       {
         int id = in.read_long ();
         String name = in.read_string ();
         String description = in.read_string ();
         int rating = in.read_long ();
         String $result = null;
         $result = this.createGroup (id, name, description, rating);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 2:  // Group/CRUD_Falae/getGroups
       {
         String $result = null;
         $result = this.getGroups ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // Group/CRUD_Falae/removeGroup
       {
         int id = in.read_long ();
         String $result = null;
         $result = this.removeGroup (id);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:Group/CRUD_Falae:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public CRUD_Falae _this() 
  {
    return CRUD_FalaeHelper.narrow(
    super._this_object());
  }

  public CRUD_Falae _this(org.omg.CORBA.ORB orb) 
  {
    return CRUD_FalaeHelper.narrow(
    super._this_object(orb));
  }


} // class CRUD_FalaePOA
