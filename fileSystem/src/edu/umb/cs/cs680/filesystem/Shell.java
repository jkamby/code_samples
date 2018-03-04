/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author JohnPaul
 *
 */
public class Shell {

	private Scanner sc;
	private String prompt;
	private FileSystem fileSystem;
	private CommandHistory cmdHist;
	/**
	 * 
	 */
	public Shell(String prompt) {
		this.sc = new Scanner(System.in);
		this.prompt = prompt;
		this.fileSystem = FileSystem.getInstance();
		this.cmdHist = new CommandHistory();
		new HashMap<String, Command>();
	}

	public FileSystem getFileSystem() {
		return this.fileSystem;
	}
	
	public void processInput() {
		System.out.print(this.prompt);
		String shellCmd = this.sc.nextLine();
		String args[] = shellCmd.split(" ");
		
		if(args.length > 0) {
			//if(args[0] == KeyStroke.getKeyStroke("UP"))
				//args[0] = "UP";
			//KeyEvent.VK_UP.paramString();
		
			switch (args[0]) {
				case "pwd":
					Command pwd = new Pwd();
					pwd.execute();
					cmdHist.push(pwd);
					break;
				
				case "cd":
					Command cd = null;
					if(args.length > 1)
						cd = new Cd(args[1]);
					else
						cd = new Cd("");
					cd.execute();
					cmdHist.push(cd);
					break;
					
				case "dir":
					Command dir = null;
					if(args.length > 1)
						dir = new Dir(args[1]);
					else
						dir = new Dir("");
					dir.execute();
					cmdHist.push(dir);
					break;
					
				case "mkdir":
					Command mkDir = null;
					if(args.length > 1)
						mkDir = new MkDir(args[1]);
					else
						mkDir = new MkDir("");
					mkDir.execute();
					cmdHist.push(mkDir);
					break;

				case "rmdir":
					Command rmDir = null;
					if(args.length > 1)
						rmDir = new RmDir(args[1]);
					else
						rmDir = new RmDir("");
					rmDir.execute();
					cmdHist.push(rmDir);
					break;
					
				case "ls":
					Command ls = null;
					if(args.length > 1)
						ls = new Ls(args[1]);
					else
						ls = new Ls("");
					ls.execute();
					cmdHist.push(ls);
					break;
					
				case "ln":
					Command ln = null;
					if(args.length > 2)
						ln = new Ln(args[1], args[2]);
					else if(args.length > 1)
						ln = new Ln(args[1], "");
					else
						ln = new Ln("", "");
					ln.execute();
					cmdHist.push(ln);
					break;
					
				case "mv":
					Command mv = null;
					if(args.length > 2)
						mv = new Mv(args[1], args[2]);
					else if(args.length > 1)
						mv = new Mv(args[1], "");
					else
						mv = new Mv("", "");
					mv.execute();
					cmdHist.push(mv);
					break;
					
				case "cp":
					Command cp = null;
					if(args.length > 2)
						cp = new Cp(args[1], args[2]);
					else if(args.length > 1)
						cp = new Cp(args[1], "");
					else
						cp = new Cp("", "");
					cp.execute();
					cmdHist.push(cp);
					break;
					
				case "chown":
					Command chown = null;
					if(args.length > 2)
						chown = new ChOwn(args[1], args[2]);
					else if(args.length > 1)
						chown = new ChOwn(args[1], "");
					else
						chown = new ChOwn("", "");
					chown.execute();
					cmdHist.push(chown);
					break;
					
				case "redo":
					Command redo = cmdHist.pop();
					redo.execute();
					break;
					
				case "history":
					CommandHistory tempHist = new CommandHistory();
					while(!cmdHist.isEmpty()) {
						tempHist.push(cmdHist.peek());
						Command cmd = cmdHist.pop();
						System.out.println(cmd.toString());
					}
					while(!tempHist.isEmpty())
						cmdHist.push(tempHist.pop());
					break;
					
				case "sort":
					Collections.sort(this.fileSystem.getCurrent().getChildren(), new AlphabeticalComparator());
					break;
				
				case "exit":
					Command exit = new Exit();
					exit.execute();
					break;
				
				default:
					System.out.println("Invalid command typed at the prompt.");
			}
		} else
			System.out.println("Please enter a command at the prompt.");
		
	}
}
