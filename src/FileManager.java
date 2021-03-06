import Entities.Car;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * File Manager class
 * Is responsible for file operations
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class FileManager {
    /**
     * Save cars array to XML file
     *
     * @param path where file will be created
     * @param cars array of cars
     * @return true - success, false - failed
     */
    public static boolean SaveCarsToXML(String path, Car[] cars) {
        XMLEncoder encoder = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            BufferedOutputStream outBuffer = new BufferedOutputStream(outputStream);
            encoder = new XMLEncoder(outBuffer);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        } finally {
            if (encoder != null) {
                encoder.writeObject(cars);
                encoder.close();
            }
        }
        return true;
    }

    /**
     * Load cars array from XML file
     *
     * @param path where file is located
     * @return cars array - success, null - fail
     */
    public static Car[] LoadCarsFromXML(String path) {
        XMLDecoder decoder = null;
        Car[] cars = null;
        try {
            FileInputStream inputStream = new FileInputStream(path);
            BufferedInputStream inBuffer = new BufferedInputStream(inputStream);
            decoder = new XMLDecoder(inBuffer);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return null;
        } finally {
            if (decoder != null) {
                cars = (Car[]) decoder.readObject();
                decoder.close();
            }
        }
        return cars;
    }

    /**
     * Is file exists?
     *
     * @param path where file is located
     * @return true - yes, false - no
     */
    public static boolean isFileExists(String path){
        return new File(path).isFile();
    }
}
