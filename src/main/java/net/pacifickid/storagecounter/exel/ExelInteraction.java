package net.pacifickid.storagecounter.exel;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.pacifickid.storagecounter.StorageCounter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class ExelInteraction {
    public static boolean toExel(Map<Item, Long> res) {
        if (res.isEmpty()) {
            return false;
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        int rowId = 0;
        for (Map.Entry<Item, Long> entry : res.entrySet()) {
            Row row = sheet.createRow(rowId++);
            Cell cellKey = row.createCell(0);
            Cell cellValue = row.createCell(1);
            cellKey.setCellValue(entry.getKey().toString());
            cellValue.setCellValue(entry.getValue().toString());
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        Path pathToFolder = FabricLoader.getInstance().getGameDir().resolve(StorageCounter.MOD_ID);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
        String fileName = "exel_" + LocalDateTime.now().format(myFormatObj) + ".xlsx";

        System.out.println(pathToFolder.toString() + File.separator + fileName);

        if (!pathToFolder.toFile().exists()) {
            pathToFolder.toFile().mkdir();
        }

        try (FileOutputStream out = new FileOutputStream(pathToFolder.toString() + File.separator + fileName)) {
            workbook.write(out);
            return true;
        } catch (IOException e) {
            StorageCounter.LOGGER.error("Failed to write exel", e);
            return false;
        }
    }
}
