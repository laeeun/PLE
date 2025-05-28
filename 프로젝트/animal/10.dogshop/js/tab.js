let tbtns = document.querySelectorAll("#tab .header button");
let contents = document.querySelectorAll("#tab .all")
tbtns.forEach(function (btn) {
  btn.addEventListener("click", function(){
    showTabContents(this.id);
  });
  btn.addEventListener("click", function(){
    changeColor(this);
  });
});

showTabContents("all");
changeColor(tbtns[0]);

function showTabContents(id) {
  let activeContents = document.querySelectorAll("." + id);

  contents.forEach(function (content) {
    content.classList.remove("active");
  })

  activeContents.forEach(function(active){
    active.classList.add("active");
  });
}

function changeColor(item){ //this : btns[0]
  tbtns.forEach(function (btn) {
    btn.classList.remove("active");
  })

  item.classList.add("active");
}
