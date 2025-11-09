package reportes;

import carga.*;
import maritimo.Buque;
import maritimo.Naviera;
import ordenes.*;
import terminal.TerminalGestionada;

public interface Visitor {
	public void visit(TerminalGestionada terminal);
	
	public void visit(Naviera naviera);
	
	public void visit(Buque buque);
	
	public void visit(OrdenDeImportacion orden);
	
	public void visit(OrdenDeExportacion orden);
	
	public void visit(Dry contenedor);
	
	public void visit(Reefer contenedor);
	
	public void visit(Tanque contenedor);
}

