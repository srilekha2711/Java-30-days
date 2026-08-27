import java.util.*;
enum DownloadStatus{
    PENDING,
    DOWNLOADING,
    COMPLETED,
    FAILED
}
class Download{
    private int downloadId;
    private String fileName;
    private double fileSize;
    private DownloadStatus status;
    Download(int downloadId, String fileName, double fileSize, DownloadStatus status){
        this.downloadId = downloadId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.status = status;

    }
    public int getDownloadId(){
        return downloadId;
    }
    public String getFileName(){
         return fileName;
    }
    public double  getFilesize(){
        return fileSize;
    }
    public DownloadStatus getDownloadStatus(){
        return status;
    }
    public void setDownloadStatus(DownloadStatus status){
        this.status = status; 
    }
    @Override
    public String toString(){
        return "ID: "+downloadId+"\n" +
                        "File: "+ fileName+ "\n" + //
                        "Size: "+fileSize+ " MB\n" + //
                        "Status: " + status;
    }
}
class DownloadTask implements Runnable{
    private Download download;
    DownloadTask(Download download){
        this.download=download;
    }
    @Override
    public void run(){
        download.setDownloadStatus(DownloadStatus.DOWNLOADING);
        System.out.println("Started downloading: "+ this.download.getFileName());
        try {
            Thread.sleep((long)(download.getFilesize() * 100));

            download.setDownloadStatus(DownloadStatus.COMPLETED);

            System.out.println(
                "Completed: " + download.getFileName()
            );

        } catch (InterruptedException e) {

            download.setDownloadStatus(DownloadStatus.FAILED);

            Thread.currentThread().interrupt();

            System.out.println(
                "Download interrupted: " + download.getFileName()
            );
        }
    }
}
public class Main {
    
    public static boolean isUnique(List<Download> downloads, int id) {

    for(Download d : downloads) {

        if(d.getDownloadId() == id) {
            return false;
        }
    }

    return true;
}
      public static void main(String args[]){
        int downloadId;
        String fileName;
        double fileSize;
        Scanner sc=new Scanner(System.in);
        int choice;
        List<Download> downloads = new ArrayList<>();
        do{
            System.out.println("========== MULTITHREADED DOWNLOADER ==========\r\n" + //
                                "\r\n" + //
                                "1. Add Download\r\n" + //
                                "2. Display Downloads\r\n" + //
                                "3. Start Downloads\r\n" + //
                                "4. Exit\r\n" + //
                                "\r\n" + //
                                "Enter choice:");
            choice=sc.nextInt();
            if(choice == 1){
                System.out.println("Enter download ID:");
                downloadId=sc.nextInt();
                sc.nextLine();
                System.out.println("Enter file name:");
                fileName=sc.nextLine();
                System.out.println("Enter file size (MB):");
                fileSize=sc.nextDouble();
                if(!isUnique(downloads,downloadId)){
                    System.out.println("This id already exits");
                    continue;
                }
                if(!fileName.isEmpty() && fileSize > 0){
                    downloads.add(new Download(downloadId, fileName, fileSize, DownloadStatus.PENDING));
                }
                else{
                    System.out.println("File name is empty or size is not greater than 0");
                }
            }
            else if(choice == 2){
                System.out.println("===== DOWNLOADS =====");
                for(Download d: downloads){
                    System.out.println(d);
                }
            }
            else if(choice == 3){
                for(int i=0; i< downloads.size();i++){
                    Download obj=downloads.get(i);
                    
                    Runnable task=new DownloadTask(obj);
                    Thread thread=new Thread(task);
                    
                    thread.start();
                    
                }
            }
        }while( choice != 4);
      }    
}
