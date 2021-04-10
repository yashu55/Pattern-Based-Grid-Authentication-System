      window.addEventListener("resize", changeDefaults);
      let checking = false;

      function changeDefaults() {
        // margin: 15.77 + window.innerWidth * 0.003846,
        // radius: 6.677 + window.innerWidth * 0.016667,
        lock.option("margin", 15 + window.innerWidth * 0.003);
        lock.option("radius", 6 + window.innerWidth * 0.015);
        lock._render();
      }

      function myResetFunction() {
      lock.enable();
      lock.reset();
      checking = false;
      document.getElementById("userPatternPassword").value = null;
      document.getElementById('mySaveBtn').style.visibility = 'visible';
      document.getElementById("notification").innerHTML = "Draw New Pattern";
      document.getElementById('mySaveBtn').innerHTML = "Save Pattern";
      document.getElementById("registerBtn").disabled = true;
      document.getElementById("notification").classList.remove("bg-warning");
      document.getElementById("notification").classList.remove("text-light");
      document.getElementById("notification").classList.remove("bg-success");



      //change field value
      //change all button to normal
      }

      function mySaveFunction() {
     
        let drawPattern = lock.getPattern();
        let drawPatternArray = drawPattern.split(',');
        if(drawPatternArray === []){
          alert("Please draw a pattern!!");
          myResetFunction();
        } else if(drawPatternArray.length < 4){
          alert("The pattern drawn is too small. Minimum length: 4");
          myResetFunction();
        } else{
          if(!checking){
          document.getElementById("userPatternPassword").value = drawPatternArray;
          document.getElementById("mySaveBtn").innerHTML = "Confirm Pattern";
          document.getElementById("notification").innerHTML = "Confirm New Pattern";
          document.getElementById("notification").classList.add("bg-warning");
          document.getElementById("notification").classList.add("text-light");
          alert("Pattern saved. Please draw again and confirm the pattern.");
          checking = true;
          lock.reset(); 
          }else{
              let originalValue =  document.getElementById("userPatternPassword").value;
              let newValue = drawPattern;
              if(originalValue != newValue){
            	lock.error();
                alert("New pattern and confirm pattern does not match. Please set again.");
                myResetFunction();
              }else if(originalValue == newValue){

                document.getElementById("notification").innerHTML = "Pattern Matched.";
                document.getElementById("notification").classList.remove("bg-warning");
                document.getElementById("notification").classList.add("bg-success");
                document.getElementById("notification").classList.add("text-light");
                document.getElementById("registerBtn").disabled = false;
                document.getElementById('mySaveBtn').style.visibility = 'hidden';
                lock.reset();
                lock.disable();
              }
          }      
        }  
      }