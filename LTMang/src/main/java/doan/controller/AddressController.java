package doan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import doan.constant.URLConstant;
import doan.service.DistrictService;
import doan.service.WardService;

@Controller
public class AddressController {

	@Autowired
	private DistrictService districtService;

	@Autowired
	private WardService wardService;

	@PostMapping(URLConstant.URL_DISTRICT)
	@ResponseBody
	public String getDistrictByProvinceId(@RequestParam int provinceId) {
		return new Gson().toJson(districtService.findByProvinceId(provinceId));
	}

	@PostMapping(URLConstant.URL_WARD)
	@ResponseBody
	public String getWardByDistrictId(@RequestParam int districtId) {
		return new Gson().toJson(wardService.findByDistrictId(districtId));
	}

}
