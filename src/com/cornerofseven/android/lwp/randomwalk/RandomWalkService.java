package com.cornerofseven.android.lwp.randomwalk;

import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class RandomWalkService extends WallpaperService{ 
	public WallpaperService.Engine onCreateEngine(){
		return new RandomWalkEngine(); 
	}

	class RandomWalkEngine extends WallpaperService.Engine{
		private RandomWalk walker;

		RandomWalkEngine(){
			SurfaceHolder holder = getSurfaceHolder();
			walker = new RandomWalk(holder,getApplicationContext());
		}

		@Override
			public void onDestroy(){
				super.onDestroy();
				walker.stopWalk();
			}

		@Override
			public void onVisibilityChanged(boolean visible){
				if(visible){
					walker.resumeWalk();
				}
				else{
					walker.pauseWalk();
				}
			}
		@Override
			public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height){
				super.onSurfaceChanged(holder,format,width,height);
				walker.setSize(width,height);
			}

		@Override
			public void onSurfaceCreated(SurfaceHolder holder){
				super.onSurfaceCreated(holder);
			}

		@Override
			public void onSurfaceDestroyed(SurfaceHolder holder){
				super.onSurfaceDestroyed(holder);
				boolean alive = true;
				walker.stopWalk();
				while(alive){
					try{
						walker.join();
						alive=false;
					}
					catch(InterruptedException iex){
					}
				}
			}
	}
}
