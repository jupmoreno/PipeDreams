package itba.eda.pipedreams.pipelogic;

public class PipeBox {
	private final static int ITEMS_SIZE = PipeFactory.getPipeTypesSize();
	private int[] items;
	private int currSize;
	
	public PipeBox() {
		items = new int[ITEMS_SIZE];
		currSize = 0;
	}
	
	public void setItem(int pipeType, int count) {
		items[pipeType] = count;
		currSize += count;
	}
	
	public boolean hasItem(int pipeType) {
		return items[pipeType] > 0;
	}
	
	public Pipe getItem(int pipeType) {
		return PipeFactory.getPipe(pipeType);
	}
	
	public void remove(int pipeType) {
		items[pipeType]--;
		currSize--;
	}
	
	public void add(int pipeType) {
		items[pipeType]++;
		currSize++;
	}
	
	public int getPipeSize() {
		return ITEMS_SIZE;
	}
	
	public int getCurrSize() {
		return currSize;
	}
	
	public boolean isEmpty() {
		return currSize == 0;
	}
}
