# LDButtonAndLoadingAnimation


To Implement this Library. Follow this Steps:

How to use blur Animation:

    initalize the variable:    

    var  simpleLoadingAnimation=SimpleLoadingAnimation(requireContext())
    
     var temp=ArrayList<String>()
     temp.add("Please Wait")
     temp.add("While We Are")
     temp.add("Processing Your Request")
    
    
     simpleLoadingAnimation.startWithRespectToApplication(requireActivity(),temp)

<img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot4.png" height="356" width="200">

<img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot2.png" height="356" width="200">

<img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot3.png" height="356" width="200">

How To Use Without BlurAnimation

<img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot1.png" height="356" width="200">

    

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.lokeshdeshmukh:LDButtonAndLoadingAnimation:Tag'
	}
	