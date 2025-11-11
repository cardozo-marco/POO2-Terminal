package busqueda;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import maritimo.Viaje;

public class FiltroORTest {
	
	private FiltroDeBusqueda filtro1;
	private FiltroDeBusqueda filtro2;
	private FiltroDeBusqueda filtro3;
	private Viaje viaje;
	
	@BeforeEach
	public void setUp() {
		filtro1 = mock(FiltroDeBusqueda.class);
		filtro2 = mock(FiltroDeBusqueda.class);
		filtro3 = mock(FiltroDeBusqueda.class);
		viaje = mock(Viaje.class);
	}
	
	@Test
	public void cumpleCuandoAlMenosUnFiltroCumpleTest() {
		when(filtro1.cumple(viaje)).thenReturn(false);
		when(filtro2.cumple(viaje)).thenReturn(true);
		when(filtro3.cumple(viaje)).thenReturn(false);
		
		List<FiltroDeBusqueda> filtros = new ArrayList<>();
		filtros.add(filtro1);
		filtros.add(filtro2);
		filtros.add(filtro3);
		
		FiltroOR filtroOR = new FiltroOR(filtros);
		assertTrue(filtroOR.cumple(viaje));
	}
	
	@Test
	public void noCumpleCuandoNingunFiltroCumpleTest() {
		when(filtro1.cumple(viaje)).thenReturn(false);
		when(filtro2.cumple(viaje)).thenReturn(false);
		when(filtro3.cumple(viaje)).thenReturn(false);
		
		List<FiltroDeBusqueda> filtros = new ArrayList<>();
		filtros.add(filtro1);
		filtros.add(filtro2);
		filtros.add(filtro3);
		
		FiltroOR filtroOR = new FiltroOR(filtros);
		assertFalse(filtroOR.cumple(viaje));
	}
	
	@Test
	public void cumpleCuandoTodosLosFiltrosCumplenTest() {
		when(filtro1.cumple(viaje)).thenReturn(true);
		when(filtro2.cumple(viaje)).thenReturn(true);
		
		List<FiltroDeBusqueda> filtros = new ArrayList<>();
		filtros.add(filtro1);
		filtros.add(filtro2);
		
		FiltroOR filtroOR = new FiltroOR(filtros);
		assertTrue(filtroOR.cumple(viaje));
	}
	
	@Test
	public void addFiltroTest() {
		List<FiltroDeBusqueda> filtros = new ArrayList<>();
		filtros.add(filtro1);
		
		FiltroOR filtroOR = new FiltroOR(filtros);
		when(filtro1.cumple(viaje)).thenReturn(false);
		when(filtro2.cumple(viaje)).thenReturn(true);
		
		assertFalse(filtroOR.cumple(viaje));
		
		filtroOR.addFiltro(filtro2);
		assertTrue(filtroOR.cumple(viaje));
	}
	
	@Test
	public void noCumpleConListaVaciaTest() {
		FiltroOR filtroOR = new FiltroOR(new ArrayList<>());
		assertFalse(filtroOR.cumple(viaje));
	}
}

