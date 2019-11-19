package imageflubber;

import java.io.*;

public class File4Img extends File {
	private int  filesInDirCount = 0;
    private int  thisFileNumberInList = 0;
	public int getThisFileNumberInList() {
		return thisFileNumberInList;
	}
	public void setThisFileNumberInList(int thisFileNumberInList) {
		this.thisFileNumberInList = thisFileNumberInList;
	}
	public int getFilesInDirCount() {
		return filesInDirCount;
	}
	public void setFilesInDirCount(int filesInDirCount) {
		this.filesInDirCount = filesInDirCount;
	}
	public File4Img (String f) {
		super(f);
	}
	public File4Img (File f, String s) {
		super(f,s);
	}
}
