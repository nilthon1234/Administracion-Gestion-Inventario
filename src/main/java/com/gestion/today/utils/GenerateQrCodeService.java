package com.gestion.today.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class GenerateQrCodeService {
    @Value("${project.poster}")
    private String basePath;

    public String generateQRCode(String tableName, String brand, String codToday, String company) {
        try {
            String directoryPath = System.getProperty("user.home")+ "/GRUPOToday-QR/" +tableName+ "/" + brand;
            /*
             *String directoryPath = basePath + tableName + "/" + brand;
             **/

            Files.createDirectories(Paths.get(directoryPath));

            // Ruta de guardado del QR
            String filePath = directoryPath+ "/" + codToday + ".png";

            // Contenido del QR
            String data = "http://localhost:8080/cod-today/" + tableName + "/" + brand + "/details/" + codToday;

            // Generar QR
            BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix);

            // Dimensiones del QR y nueva imagen con texto
            int width = qrImage.getWidth();
            int height = qrImage.getHeight();
            int newHeight = height + 50; // Espacio extra para texto

            BufferedImage finalImage = new BufferedImage(width, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = finalImage.createGraphics();

            // Fondo blanco
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, newHeight);

            // Texto superior (Brand y tabla)
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            int textX = (width - fm.stringWidth(brand + " | " + tableName)) / 2;
            g2d.drawString(brand + " | " + tableName, textX, 20);

            // Dibujar QR
            g2d.drawImage(qrImage, 0, 25, null);

            // Texto inferior (cod_today)
            int codTextX = (width - fm.stringWidth(codToday)) / 2;
            g2d.drawString(codToday, codTextX, newHeight - 5);

            g2d.dispose();

            // Guardar imagen final
            ImageIO.write(finalImage, "PNG", new File(filePath));

            return "QR save in: " + filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar QR";
        }
    }


}
