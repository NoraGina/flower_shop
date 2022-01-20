/* TOGGLE FILTER NAVBAR HAMBURGER */
      const filterToggleButton=document.querySelector('#toggleFilterButton');
      const navFilterList = document.querySelector('#navFilterList');
      filterToggleButton.addEventListener('click', ()=>{
          navFilterList.classList.toggle('filterActive');
      })

      /* TOGGLE  NAVBAR HAMBURGER */
      const toggleButton = document.querySelector('#toggleButton');
      const naviList = document.querySelector('#naviList');
      const userDivId = document.querySelector('#userDivId')
       toggleButton.addEventListener('click', () => {
            naviList.classList.toggle('active');
            userDivId.classList.toggle('active');
        })

    /*Function to open and close Modal*/
    function openMyModal(){
       let modals = document.querySelectorAll('.modal.myModal')
              // Get the button that opens the modal
        let btns = document.querySelectorAll('.openmodal');
        let spans = document.querySelectorAll(".close");
            for (let i = 0; i < btns.length; i++) {
                btns[i].onclick = function () {
                    modals[i].classList.remove('closeModal');
                    modals[i].classList.add('showModal')
                }
            }
            for (let i = 0; i < spans.length; i++) {
                spans[i].onclick = function () {
                    modals[i].classList.remove('showModal');
                    modals[i].classList.add('closeModal');
                }
            }
    }

    /* *** CARUSEL **** */
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

        setTimeout(showSlides, 3000); // Change image every 3 seconds
      }