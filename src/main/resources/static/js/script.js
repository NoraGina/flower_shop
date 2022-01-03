function togglePassword() {
    let x = document.querySelector("#pwd");
    if (x.type === "password") {
      x.type = "text";
    } else {
      x.type = "password";
    }
  }

   let modals = document.getElementsByClassName('modal myModal');
              // Get the button that opens the modal
              let btns = document.getElementsByClassName("openmodal");
             let spans = document.getElementsByClassName("close");
              for (let i = 0; i < btns.length; i++) {
                  btns[i].onclick = function () {
                      modals[i].style.display = "block";
                  }
              }
              for (let i = 0; i < spans.length; i++) {
                  spans[i].onclick = function () {
                      modals[i].style.display = "none";
                  }

  }
  const toggleButton = document.getElementById('toggleButton');
          const naviList = document.getElementById('naviList');
          toggleButton.addEventListener('click', () => {
              naviList.classList.toggle('active');
          })

  let slideIndex = 0;
          showSlides();

          function showSlides() {
            let i;
            let slides = document.getElementsByClassName("mySlides");

            for (i = 0; i < slides.length; i++) {
              slides[i].style.display = "none";
            }
            slideIndex++;
            if (slideIndex > slides.length) {slideIndex = 1}

            slides[slideIndex-1].style.display = "block";

            setTimeout(showSlides, 3000); // Change image every 2 seconds
          }


        /* TOGGLE FILTER NAVBAR HAMBURGER */
        const filterToggleButton=document.getElementById('toggleFilterButton');
        const navFilterList = document.getElementById('navFilterList');
        filterToggleButton.addEventListener('click', ()=>{
            navFilterList.classList.toggle('filterActive');
        })