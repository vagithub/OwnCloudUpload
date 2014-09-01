package org.owncloudupload.watcher;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import org.owncloudupload.settings.ServerConfig;

public class DirectoryMonitor extends Thread{


    private WatchService watcher;
    private Map<WatchKey,Path> keys;
    private boolean recursive;
    private boolean trace = false;   
	private ServerConfig config;
    private Boolean stop = false;
    private File dir;
    private static final String WEBDAV_PATH = "remote.php/webdav/";
 
    public ServerConfig getConfig() {
		return config;
	}

	public void setConfig(ServerConfig config) {
		this.config = config;
	}

	@SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Creates a WatchService and registers the given directory
     */
    DirectoryMonitor(File dir, boolean recursive, ServerConfig srvConfig) throws IOException {
    	config = srvConfig;
    	this.dir = dir;
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        this.recursive = recursive;

        if (recursive) {
            System.out.format("Scanning %s ...\n", dir);
            registerAll(dir.toPath());
            System.out.println("Done.");
        } else {
            register(dir.toPath());
        }

        // enable trace after initial registration
        this.trace = true;
    }

    static void usage() {
        System.err.println("usage: java WatchDir [-r] dir");
        System.exit(-1);
    }

    public static void mainA(String[] args) throws IOException {
        // parse arguments
        if (args.length == 0 || args.length > 2)
            usage();
        boolean recursive = false;
        int dirArg = 0;
        if (args[0].equals("-r")) {
            if (args.length < 2)
                usage();
            recursive = true;
            dirArg++;
        }

        // register directory and process its events
        Path dir = Paths.get(args[dirArg]);
        new WatchDir(dir, recursive).processEvents();
    }
    
    private void upload(Path file) throws FileNotFoundException, IOException {
    	
    	Sardine sardine = SardineFactory.begin(config.getUser(),config.getPassword());
    	String URL;
    	String pathOnServer = file.toString();
    	String root = dir.getAbsolutePath();
    	int trimEnd = root.length();
    	
    	pathOnServer = pathOnServer.substring(pathOnServer.indexOf(root)+ trimEnd +1);
		pathOnServer = pathOnServer.replace(file.toFile().separatorChar, '/');
    	if(config.getServerURL().endsWith("/"))
    	{    		
    		URL = config.getServerURL() + WEBDAV_PATH + pathOnServer;
    	}
    	else
    	{    		
    		URL = config.getServerURL() + "/"+ WEBDAV_PATH + pathOnServer;
    	}
    	URL.replace(" ", "%20");
//    	sardine.put(URL, new FileInputStream(name.toFile()));
    	System.out.println("$$$$$$ " + file.toAbsolutePath() + " &&  && "+ URL);
    }
    
   
    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }       
	@Override
	public void run() {
		  while (!stop) {

	            // wait for key to be signalled
	            WatchKey key;
	            try {
	                key = watcher.take();
	            } catch (InterruptedException x) {
	                return;
	            }

	            Path dir = keys.get(key);
	            if (dir == null) {
	                System.err.println("WatchKey not recognized!!");
	                continue;
	            }

	            for (WatchEvent<?> event: key.pollEvents()) {
	                WatchEvent.Kind kind = event.kind();

	                // TBD - provide example of how OVERFLOW event is handled
	                if (kind == OVERFLOW) {
	                    continue;
	                }

	                // Context for directory entry event is the file name of entry
	                WatchEvent<Path> ev = cast(event);
	                Path name = ev.context();
	                Path child = dir.resolve(name);

	                // print out event
	                System.out.format("%s: %s\n", event.kind().name(), child);

	                // if directory is created, and watching recursively, then
	                // register it and its sub-directories
	                if (recursive && (kind == ENTRY_CREATE)) {
	                    try {
	                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
	                            registerAll(child);
	                            Files.walkFileTree(child, new SimpleFileVisitor<Path>() {
	                                @Override
	                                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	                                    throws IOException
	                                {
	                                    upload(file);
	                                    return FileVisitResult.CONTINUE;
	                                }
	                            });
	                        }
	                       Thread.sleep(config.getTimeBeforeSynch()*60000);
	                        upload(child);
	                    } catch (IOException x) {
	                        // ignore to keep sample readbale
	                    } catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                else if(kind == ENTRY_CREATE){
	                       try {
							Thread.sleep(config.getTimeBeforeSynch()*60000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

	                	try {
							upload(child);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                //TODO modified and removed
	            }

	            // reset key and remove from set if directory no longer accessible
	            boolean valid = key.reset();
	            if (!valid) {
	                keys.remove(key);

	                // all directories are inaccessible
	                if (keys.isEmpty()) {
	                    break;
	                }
	            }
	        }
	}
}
