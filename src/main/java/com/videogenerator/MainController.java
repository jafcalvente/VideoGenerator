package com.videogenerator;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST Service for sending images.
 * 
 * @author jafcalvente
 *
 */
@Controller
public class MainController {
	
	/** Logger. */
	private static Logger log = Logger.getLogger(MainController.class.getName());

	/** Constants. */
	public static final String ABSOLUTE_PATH = "D:/WS_J2EE/WS_TESTS/VideoGenerator/src/main/resources/snapshots/scene_";
	public static final String RELATIVE_PATH = "snapshots/scene_";
	public static final String EXTENSION = ".png";
	public static final String ERROR_TAG = "ERROR-";
	
	private int imageIndex = 0;
	private int TOTAL_IMAGES = 100;

	@ResponseBody
	@RequestMapping(value = "/getImage", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getImage() throws Exception {
		
//		String imagePath = ABSOLUTE_PATH + imageIndex + EXTENSION;
//		byte[] imageBytes = Base64Utils.encode(FileUtils.readFileToByteArray(new File(imagePath)));
		
		String imagePath = RELATIVE_PATH + imageIndex + EXTENSION;
		InputStream is = getClass().getClassLoader().getResourceAsStream(imagePath);
		byte[] imageBytes = Base64Utils.encode(IOUtils.toByteArray(is));
		imageIndex = (imageIndex + 1) % TOTAL_IMAGES;
		
		log.info("Sending image " + imagePath);
		return new ResponseEntity<byte[]>(imageBytes, HttpStatus.OK);
	}

}
