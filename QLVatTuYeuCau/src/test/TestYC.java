/**
 * 
 */
package test;

import java.util.ArrayList;
import java.util.HashMap;

import dao.YeuCauDAO;
import model.YeuCau;

/**
 * @author camnh_000
 *
 */
public class TestYC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		YeuCauDAO yeuCauDAO =  new YeuCauDAO();
		HashMap<Integer, Integer> yeuCauHash = new HashMap<Integer, Integer>();
//		for(CongVan congVan: congVanList){
	ArrayList<YeuCau> yeuCauList = (ArrayList<YeuCau>) yeuCauDAO.getVTThieu();
	for(YeuCau yeuCau: yeuCauList){
			int ctVtId = yeuCau.getCtVatTu().getCtvtId();
			Integer slCu = yeuCauHash.get(ctVtId);
			Integer soluong = yeuCau.getYcSoLuong();
			if (slCu != null)
				soluong += slCu;
			yeuCauHash.put(ctVtId,soluong);
		}
	for (int key : yeuCauHash.keySet())
		System.out.println(yeuCauHash.get(key));
	}
	

}
