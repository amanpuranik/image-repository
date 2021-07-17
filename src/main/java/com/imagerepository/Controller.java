package com.imagerepository;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
public class Controller {

    @Autowired
    userRepository userRepository;

    @CrossOrigin(origins = "http://localhost:4200")

//    @GetMapping(path = "names")
//    public List<model> getNames(){
//      //  return //
//    }

    @GetMapping(path = "test")
    public List<model> list(){
        return userRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/upload")
    public ResponseEntity.BodyBuilder uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        model img = new model(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));
        userRepository.save(img);
        return ResponseEntity.status(HttpStatus.OK);
    }

    //test comment

    //used to return type model and returned img
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping(path = { "/get/{imageName}" }, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable("imageName") String imageName) throws IOException {

        final Optional<model> retrievedImage = userRepository.findByName(imageName);
        model img = new model(retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));

        InputStream in = getClass().getResourceAsStream("com/imagerepository/images/test_image.jpeg");

        byte [] bytes = StreamUtils.copyToByteArray(in);

        return decompressBytes(retrievedImage.get().getPicByte()); //this part of the code gives me the actual image from postman

    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {

        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

}
