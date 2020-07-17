# LDButtonAndLoadingAnimation

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
How to use  Blur Animation:
    
     var  simpleLoadingAnimationWithBlur=SimpleLoadingAnimationWithBlur(requireContext()) //Initalize
     
     var temp=ArrayList<String>()
     temp.add("Please Wait")
     temp.add("While We Are")
     temp.add("Processing Your Request")
     
     simpleLoadingAnimationWithBlur.setBottomTextVisibleDuration(4000) //optional
     
     //To call with Bottom Text
     simpleLoadingAnimationWithBlur.startWithRespectToApplication(requireActivity(),temp)
     
     //To call without Bottom Text
     simpleLoadingAnimationWithBlur.startWithRespectToApplication(requireActivity())
     
     //to finish
     simpleLoadingAnimationWithBlur.finish()
     
<img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot5.png" height="356" width="170"><img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot6.png" height="356" width="170">

How to use without Blur Animation:

    initalize the variable:    

    var  simpleLoadingAnimation=SimpleLoadingAnimation(requireContext())
    
     var temp=ArrayList<String>()
     temp.add("Please Wait")
     temp.add("While We Are")
     temp.add("Processing Your Request")
    
    
     simpleLoadingAnimation.startWithRespectToApplication(requireActivity(),temp)

<img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot4.png" height="356" width="170"><img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot2.png" height="356" width="170"><img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot3.png" height="356" width="170">

How To Use it wihtout Explaining Text:
        
     var  simpleLoadingAnimation=SimpleLoadingAnimation(requireContext())
        
     simpleLoadingAnimation.startWithRespectToApplication(requireActivity())

<img src="https://github.com/lokeshdeshmukh/LDButtonAndLoadingAnimation/blob/master/Screenshot1.png" height="356" width="170">

    


	