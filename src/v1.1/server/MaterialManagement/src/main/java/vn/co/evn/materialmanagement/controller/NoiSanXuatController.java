package vn.co.evn.materialmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.co.evn.materialmanagement.entity.NoiSanXuat;
import vn.co.evn.materialmanagement.service.NoiSanXuatService;

@RestController
@RequestMapping("/noisanxuat")
public class NoiSanXuatController {

	/** The noi san xuat service. */
	@Autowired
	private NoiSanXuatService noiSanXuatService;

	/**
	 * get NoiSanXuat
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<NoiSanXuat>> getNoiSanXuat() {
		List<NoiSanXuat> nsxList = noiSanXuatService.getNoiSanXuat(null);
		if (nsxList == null || nsxList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(nsxList, HttpStatus.OK);
	}

	/**
	 * get NoiSanXuat
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<NoiSanXuat> getNoiSanXuatById(@PathVariable("id") String nsxMa) {
		List<NoiSanXuat> nsxList = noiSanXuatService.getNoiSanXuat(null);
		NoiSanXuat nsx = null;

		if (nsxList == null || nsxList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		nsx = nsxList.get(0);
		return new ResponseEntity<>(nsx, HttpStatus.OK);
	}
}
