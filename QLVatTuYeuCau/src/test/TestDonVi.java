package test;

import model.DonVi;
import dao.DonViDAO;

public class TestDonVi {
	
	public TestDonVi() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		new DonViDAO().addDonVi(new DonVi("DV1", "Don vi 1", "09999999999", "qu@a.c", "Ag",0));
	}
	
}
