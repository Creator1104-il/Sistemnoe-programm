import java.io.*;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String From = "C:\\CopyThere\\From.txt";
        String From2 = "C:\\CopyThere\\From2.txt";
        String To = "C:\\CopyThere\\To.txt";
        String To2 = "C:\\CopyThere\\To2.txt";
        String To3 = "C:\\CopyThere\\To3.txt";
        String To4 = "C:\\CopyThere\\To4.txt";
        Copy(From, To);
        Copy(From2, To2);
        long endTime = System.currentTimeMillis();
        System.out.println("Время последовательного копирования " + (endTime-startTime));
        parallelCopy(From, From2, To3, To4);
    }
    //лаба 5 паралельное и нет копирование файлов
    static void Copy(String from, String To){
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        String line;
        try {
            bufferedReader = new BufferedReader(new FileReader(from));
            bufferedWriter = new BufferedWriter(new FileWriter(To));
            line = bufferedReader.readLine();
            while (line != null){
                bufferedWriter.write(line + "\n");
                bufferedWriter.flush();
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода/вывода" + e.getMessage());
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии потока " + e.getMessage());
            }
        }
    }
    static void parallelCopy(String From, String From2, String To, String To2){
        long startTime = System.currentTimeMillis();
        //поток для копирования файла
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Copy(From, To);
            }
        });
        //второй поток для копирования файла
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Copy(From2, To2);
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Ошибка ожидания завершения");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Время параллельного копирования " + (endTime-startTime));
    }
}