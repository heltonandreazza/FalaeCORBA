package Group;


/**
* Group/CRUD_FalaeOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Group_Module.idl
* Ter�a-feira, 3 de Maio de 2016 21h51min18s BRT
*/

public interface CRUD_FalaeOperations 
{
  String inserir_grupo ();
  String createGroup (int id, String name, String description, int rating);
  String getGroups ();
  String removeGroup (int id);
} // interface CRUD_FalaeOperations