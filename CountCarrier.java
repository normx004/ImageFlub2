package imageflubber;

public class CountCarrier {
	    private int  filesSaved = 0;
	    private int  filesDeleted = 0;
	    private int  filesMoved =0; 
	    private int  filesCopyed =0; 
	    
	    private long bytesDeleted = 0;
	    
	    
	    public int  getFilesSaved() {return filesSaved;}
		public void setFilesSaved(int filesSaved) {	this.filesSaved = filesSaved;}
		public int  getFilesMoved() {return filesMoved;}
		public void setFilesMoved(int filesMoved) {	this.filesMoved = filesMoved;}
		public int  getFilesCopyed() {return filesCopyed;}
		public void setFilesCopyed(int filesCopyed) {	this.filesCopyed = filesCopyed;}
		public int  getFilesDeleted() {	return filesDeleted;}
		public void setFilesDeleted(int filesDeleted) {	this.filesDeleted = filesDeleted;}
		
		public long getBytesDeleted() {
			return bytesDeleted;
		}
		public void setBytesDeleted(long bytesDeleted) {
			this.bytesDeleted = bytesDeleted;
		}
	

}
