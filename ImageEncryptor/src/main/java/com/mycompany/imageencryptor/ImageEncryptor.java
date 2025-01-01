/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.imageencryptor;

/**
 *
 * @author nanribet
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ImageEncryptor {

    private static final String ENCRYPTION_KEY = "mySecretKey"; // Replace with your key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the path of the image file:");
        String imagePath = scanner.nextLine();

        System.out.println("Do you want to (1)Encrypt or (2)Decrypt the image? Enter 1 or 2:");
        int choice = scanner.nextInt();

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            if (choice == 1) {
                BufferedImage encryptedImage = processImage(image, true);
                ImageIO.write(encryptedImage, "png", new File("encrypted_image.png"));
                System.out.println("Image encrypted successfully! Saved as 'encrypted_image.png'.");
            } else if (choice == 2) {
                BufferedImage decryptedImage = processImage(image, false);
                ImageIO.write(decryptedImage, "png", new File("decrypted_image.png"));
                System.out.println("Image decrypted successfully! Saved as 'decrypted_image.png'.");
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (IOException e) {
            System.out.println("Error reading or writing image file: " + e.getMessage());
        }
    }

    private static BufferedImage processImage(BufferedImage image, boolean encrypt) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage processedImage = new BufferedImage(width, height, image.getType());

        int key = generateKey(ENCRYPTION_KEY);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);

                // Encrypt or decrypt pixel
                int processedRgb = encrypt ? rgb ^ key : rgb ^ key;

                processedImage.setRGB(x, y, processedRgb);
            }
        }
        return processedImage;
    }

    private static int generateKey(String key) {
        int hash = 7;
        for (int i = 0; i < key.length(); i++) {
            hash = hash * 31 + key.charAt(i);
        }
        return hash;
    }
}
