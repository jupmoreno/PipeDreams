package itba.eda.pipedreams.enginelogic;

import itba.eda.pipedreams.Method;
import itba.eda.pipedreams.pipelogic.Pipe;
import itba.eda.pipedreams.pipelogic.PipeBox;
import itba.eda.pipedreams.tablelogic.Board;
import itba.eda.pipedreams.tablelogic.Dir;
import itba.eda.pipedreams.tablelogic.Point;

import java.util.Deque;
import java.util.LinkedList;

public class Engine {

	private Board board;
	private Method method;
	private PipeBox pipeBox;

	private boolean iterative;

	private int longestPathSize;

	public Engine(Board board, Method method, int[] sizes) {
		this.board = board;
		this.method = method;
		this.pipeBox = new PipeBox(sizes);
		this.longestPathSize = pipeBox.size() + pipeBox.get(Pipe.CROSS);
	}

	public void start() {
		Timer timer = new Timer();
		timer.startClock();
		switch(method) {
			case EXACT:
				backtracking();
				break;
		}
		timer.stopClock();
	}

	private void backtracking() {
		if(iterative) {

		} else {
			Deque<Pipe> longestPath = new LinkedList<Pipe>(); // TODO: Pipe + Point??
			Deque<Pipe> currPath = new LinkedList<Pipe>();
			backtrackingRec(Board.getNext(board.getStartPoint(), board.getStartFlow()), board.getStartFlow().opposite(), currPath, longestPath);
		}
	}

	private void backtrackingRec(Point point, Dir from, Deque<Pipe> currentPath, Deque<Pipe> longestPath) {
//		board.print();

		if(bestSolution(longestPath)) {
			System.out.println("Ya no puede haber mejores soluciones");
			return;
		}

		if(!board.withinLimits(point)) {
			if(currentPath.size() > longestPath.size()){
				System.out.println("Soy una mejor solucion de longitud: " + (currentPath.size() + 1)); // Logging
				copyDeque(currentPath, longestPath); // TODO: Ask if this is the longest possible path, where?
			}
			return;
		}

		if(!board.isEmpty(point)) {
			Pipe pipe = board.getPipe(point);
			if(pipe == Pipe.CROSS) {
				currentPath.push(pipe);
				backtrackingRec(Board.getNext(point, pipe.flow(from)), pipe.flow(from).opposite(), currentPath, longestPath);
				currentPath.pop();
			}
			return;
		}

		if(pipeBox.isEmpty()) {
			return;
		}

		for(Pipe pipe : pipeBox) {
			if(pipe.canFlow(from) && pipeBox.get(pipe) > 0) {
				pipeBox.remove(pipe);
				board.putPipe(pipe, point);
				currentPath.push(pipe);

				backtrackingRec(Board.getNext(point, pipe.flow(from)), pipe.flow(from).opposite(), currentPath, longestPath);

				currentPath.pop();
				board.removePipe(point);
				pipeBox.add(pipe);

				if(bestSolution(longestPath)) {
					System.out.println("Ya no puede haber mejores soluciones");
					return;
				}
			}
		}

	}

	private boolean bestSolution(Deque<Pipe> longestPath) {
		return longestPath.size() == longestPathSize;
	}

	private <T> void copyDeque(Deque<T> from, Deque<T> to) { // TODO: Better way?
		to.clear();

		for (T aux : from) {
			to.add(aux);
		}
	}
}
