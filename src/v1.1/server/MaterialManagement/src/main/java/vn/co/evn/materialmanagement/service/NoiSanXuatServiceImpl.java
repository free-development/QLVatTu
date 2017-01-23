/**
 * 
 */
package vn.co.evn.materialmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import vn.co.evn.materialmanagement.ApplicationInit;
import vn.co.evn.materialmanagement.entity.NoiSanXuat;
import vn.co.evn.materialmanagement.utils.Utils;

/**
 * @author quoip
 *
 */
@SuppressWarnings("unchecked")
@Service
public class NoiSanXuatServiceImpl implements NoiSanXuatService {

	private static List<NoiSanXuat> nsxList = new ArrayList<>();

	static {
		nsxList = (List<NoiSanXuat>) Utils.readJsonFile("D:/opt/materialmanagement/dataStub/NoiSanXuatStub.json", new TypeReference<List<NoiSanXuat>>() {});
	}
	
	@Override
	public List<NoiSanXuat> getNoiSanXuat(NoiSanXuat noiSanXuat) {
		return nsxList;
	}

	@Override
	public NoiSanXuat getNoiSanXuatById(String noiSanXuatMa) {
		nsxList = (List<NoiSanXuat>) Utils.readJsonFile(ApplicationInit.getConfig().getRootDirectory() + "dataStub/NoiSanXuatStub.json", new TypeReference<List<NoiSanXuat>>() {});
		NoiSanXuat noiSanXuat = null;
		if (nsxList != null || !nsxList.isEmpty()) {
			noiSanXuat = nsxList.get(0);
		}
		return noiSanXuat;
	}
}
