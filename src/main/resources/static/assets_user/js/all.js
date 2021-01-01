var url123 = "https://wealthland.herokuapp.com";
$(document).ready(function () {
    $('[data-original]').lazyload({
        threshold: 200
    })

    $('.loading-overlay').css('opacity', '0')
    setTimeout(
        function () {
            $('.loading-overlay').css('display', 'none')
            // $('.loading-overlay').css('z-index', '-1000')
            
        },
        1000
    )


})
var lastScrollTop = 0;

var isFixed = false

const breakPoint = 300;

$(document).click(function() {

    const nav = $('.nav-list.show')

    if (nav) {
        hideMenu()
    }
});

$("li").click(function(event) {
    event.stopPropagation();
});

$(window).scroll(() => {
    const { pageYOffset, innerWidth } = window

    var st = $(this).scrollTop();
    if (st > lastScrollTop) {
        // downscroll code

        if (pageYOffset < breakPoint && isFixed) {
            $('header').removeClass('fixed')
            $('header').removeClass('show')

            isFixed = false
        }

        if (pageYOffset >= breakPoint && isFixed) {
            $('header').removeClass('fixed')
            $('header').removeClass('show')

            isFixed = false
        }

    } else {

        if (pageYOffset >= breakPoint && !isFixed) {
            $('header').addClass('fixed')

            setTimeout(
                function () {
                    $('header').addClass('show')
                },
                0
            )

            isFixed = true
        }
        // upscroll
        // isFixed = false

        if (pageYOffset <= 10) {
            $('header').removeClass('fixed')
            $('header').removeClass('show')
        }
    }

    lastScrollTop = st;

})

function showMenu() {
    setTimeout(function() {
        $('.nav-list').addClass('show')
        $('.menu-backdrop').addClass('show')
    
        $('html').css('overflow','hidden')
        $('body').css('overflow', 'hidden')
    }, 0)

}

function hideMenu() {
    $('.nav-list').removeClass('show')
    $('.menu-backdrop').removeClass('show')

    $('html').css('overflow','auto')
    $('body').css('overflow', 'auto')
}
var estates = {} || estates
window.dataLayer = window.dataLayer || [];

function gtag() {
    dataLayer.push(arguments);
}

gtag('js', new Date());

gtag('config', 'UA-162930232-1');

estates.initHotProject = function () {
    $.ajax({
        url: url123 + "/api/hotEstate/",
        method: "GET",
        dataType: "json",
        success: function (data) {
            $('#hotEstates').empty();
            $.each(data, function (index, estates) {
                if (index == 0) {
                    $('#hotEstates').append(
                        `
               <figure class="hot-project" id="hotEstates"><img
                    src="upload/${estates.image}"/>
                <figcaption>
                    <p class="hot-project__title">${estates.name}</p>
                    <p class="hot-project__description">${estates.frontDescription}</p>
                    <a class="link-project"
                                                 href="/estate?id=${estates.id}">XEM CHI TIẾT
                    >></a>
                </figcaption>
            </figure>
                            `
                    );
                } else {
                    $('#hotEstates').append(
                        `<figure class="hot-project"><img
                    src="upload/${estates.image}"/>
                <figcaption>
                    <p class="hot-project__title">${estates.name}</p>
                    <p class="hot-project__description">${estates.frontDescription}</p><a class="link-project"
                                                     href="/estate?id=${estates.id}">XEM CHI TIẾT
                    >></a>
                </figcaption>
            </figure>`
                    );
                }
            });
        }
    })
};
estates.drawTable = function () {
    $.ajax({
        url: url123 + "/api/categorys",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#category_estates').empty();
            $.each(data, function (index, value) {
                $('#category_estates').append(
                    `
              <li>
                <div><a id="estates-all-${value.id}" onclick="estates.getData(${value.id})"  href="javascript:;"><i class="fa fa-bookmark-o" aria-hidden="true"></i>${value.name}</a>
                  
                </div>
              </li>

              `
                );
                $('#estates-all-0').addClass("active");
            });
        }
    });
}
estates.getData = function (id) {
    $('[id^="estates-all-"]').removeClass("active");
    $('#estates-all-' + id).addClass("active");
    if (id == 0) {
        $.ajax({
            url: url123 + "/api/estates/",
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                $('#call-data-api').empty();
                $.each(data, function (index, value) {
                    $('#call-data-api').append(
                        `
                <div  class="col-12 col-sm-6 col-md-4" >
                <figure class="gallery-figure-room"><img src="upload/${value.image}"/>
                  <div class="title text-medium">
                    <h4>${value.name}</h4>
                    <div class="sub-info"><i class="fa fa-book"></i><span>Quy mô dự án: 18.000 m2</span></div>
                    <div class="sub-info"><i class="fa fa-bookmark-o"></i><span>Quy mô dự án: 48m2 - 96m2</span></div>
                  </div><a class="float-title hover-darken ease-out" href="/estate?id=${value.id}"> XEM CHI TIẾT</a>
                </figure>
              </div>
                `
                    );
                });
            }
        });
    }
    $.ajax({
        url: url123 + "/api/estateCategory/" + id,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#call-data-api').empty();
            $.each(data, function (index, value) {
                $('#call-data-api').append(
                    `
                <div  class="col-12 col-sm-6 col-md-4" >
                <figure class="gallery-figure-room"><img src="upload/${value.image}"/>
                  <div class="title text-medium">
                    <h4>${value.name}</h4>
                    <div class="sub-info"><i class="fa fa-book"></i><span>Quy mô dự án: 18.000 m2</span></div>
                    <div class="sub-info"><i class="fa fa-bookmark-o"></i><span>Quy mô dự án: 48m2 - 96m2</span></div>
                  </div><a class="float-title hover-darken ease-out" href="/estate?id=${value.id}"> XEM CHI TIẾT</a>
                </figure>
              </div>
                `
                );
            });
        }
    });
}
estates.news = function () {
    $.ajax({
        url: url123 + "/api/newss",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#news').empty();
            $.each(data, function (index, value) {
                if(index < 3) {
                    $('#news').append(
                        `
               <div class="col-12 col-sm-6 col-md-4"><a class="news-figure-vertical">
        <figure class="news-figure-vertical__preview"><img
    src="upload/${value.image}"/>
        <div class="date-tag bg-accent"><span class="date">${value.date}</span><span class="month">${value.month}</span></div>
    </figure>
    <div class="main-title-wrapper">
        <div class="main-title text-medium"><a href="/blogDetails?id=${value.id}">${value.title1}</a>
    </div>
    </div>
    <div class="split-line">
        <div></div>
        </div>
        <div class="short-description text-medium">
        <p>${value.checkin}</p>
    </div>
    </a></div>
                `
                    );
                }
            });

        }
    });
}
var clients = {} || clients
clients.save = function() {
        var clientObj = {};
        clientObj.name = $('#name').val();
        clientObj.tel = $('#tel').val();
        clientObj.email = $('#email').val();
        clientObj.description = $('#description').val();
        //
        $.ajax({
            url: url123 + "/api/clients/",
            method: "POST",
            dataType: "json",
            contentType: "application/json",
            data: JSON.stringify(clientObj),
            done: function () {
                console.log("POST DONE");
                alert("Thank! We will contact you soon!!")
            },
            success: function (data) {
                console.log("POST success");
                alert("Thank! We will contact you soon!!")

            }
        });
}
var banners = {} || banners
banners.show = function () {
    $.ajax({
        url: url123 + "/api/banners",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#banners').empty();
            $.each(data, function (index, value) {
                if(index < 5) {
                    $('#banners').append(
                        `
               <div class="carousel-container">
        <div id="wowslider-container1">
            <div class="ws_images">
                <ul>
                    <li><a href="http://wowslider.net"><img src="libs/wowslider/data/images/sp9_master.jpg" alt=""
                                                            title=""/></a></li>
                    <li><a href="http://wowslider.net"><img src="libs/wowslider/data/images/sp8_master.jpg"
                                                            alt="image carousel" title=""/></a></li>
                    <li><a href="http://wowslider.net"><img
                            src="libs/wowslider/data/images/5yeutotaonencohoidaututhongminhtaiflcluxuryhotelsamson2578643.jpg"
                            title=""
                            alt="5-yeu-to-tao-nen-co-hoi-dau-tu-thong-minh-tai-flc-luxury-hotel-sam-son-257-8643"/></a>
                    </li>
                </ul>
                <div class="ws_bullets"><a href="#" title=""><span><img class="tooltip__img"
                                                                        src="libs/wowslider/data/images/sp9_master.jpg"
                                                                        alt=""/><span>1</span></span></a><a href="#"
                                                                                                            title="sp8_master"><span><img
                        class="tooltip__img" src="libs/wowslider/data/tooltips/sp8_master.jpg"
                        alt="sp8_master"/><span>2</span></span></a><a href="#"
                                                                      title="5-yeu-to-tao-nen-co-hoi-dau-tu-thong-minh-tai-flc-luxury-hotel-sam-son-257-8643"><span><img
                        class="tooltip__img"
                        src="libs/wowslider/data/tooltips/5yeutotaonencohoidaututhongminhtaiflcluxuryhotelsamson2578643.jpg"
                        alt="5-yeu-to-tao-nen-co-hoi-dau-tu-thong-minh-tai-flc-luxury-hotel-sam-son-257-8643"/><span>3</span></span></a>
                </div>
                <div class="ws_script" style="position:absolute;left:-99%"><a href="http://wowslider.net">slider
                    jquery</a><span>by WOWSlider.com v9.0m</span></div>
            </div>
            <div class="ws_shadow"></div>
        </div>
        <div class="carousel-widget app-container" id="modalAddEdit" name="modalAddEdit">
            <div class="project-description">
                <h2 class="feature text-2">BABYLON RESIDENCE</h2><span class="feature-description">458 Minh Khai, Hai Bà Trưng
            - Hà Nội</span><span class="feature-description">Giá chỉ từ 4 tỷ</span>
                <button class="btn btn-accent">Xem chi tiết</button>
            </div>
            <form id="formAddEdit" th:name="formAddEdit">
                <input type="text" id="name" placeholder="Vui lòng nhập họ tên" required/>
                <input type="number" id="tel" placeholder="Số điện thoại" required minlength="10" maxlength="11"/>
                <input type="email" id="email" placeholder="Email"/>
                <textarea  id="description" placeholder="Viết bình luận" rows="4" required></textarea>
                <a href="javascript:" style="text-align: center" class="btn btn-accent" onclick="clients.save()">Đăng ký tư vấn</a>
            </form>
        </div>
    </div>
                `
                    );
                }
            });

        }
    });
}
// About
var introduces = {} || introduces
introduces.drawIntroduction = function (){
    $.ajax({
        url: url123 + "/api/introduces",
        method : 'GET',
        dataType : 'json',
        success : function(data){
            $('#abouts').empty();
            var url = window.location.search;
            // alert(url);
            $.each(data, function(index, value){
                $('#abouts').append(
                    `
                <div  class="col-12 col-md-4" v-for="intro in list">
                    <button id="introduct-all-${value.id}" onclick="introduces.getData(${value.id})" class="btn btn-outline">${value.name}</button>
                </div>
              `
                );
            });
            $.each(data, function(index, value) {
                if(url == `?tab=${value.id}`){
                    $(`#introduct-all-${value.id}`).addClass("active");
                    introduces.getData(`${value.id}`)
                }
                // if (url == `?tab=32`) {
                //     $('#introduct-all-32').addClass("active");
                //     introduces.getData(32);
                // }
                // if (url == '?tab=28') {
                //     $('#introduct-all-28').addClass("active");
                //     introduces.getData(28);
                //
                // }
                // if (url == '?tab=35') {
                //     $('#introduct-all-35').addClass("active");
                //     introduces.getData(35);
                // }
            });
        }
    });
}
introduces.getData=function (id){
    $('[id^="introduct-all-"]').removeClass("active");
    $('#introduct-all-'+id).addClass("active");
    $.ajax({
        url: url123 + "/api/layouts/" +id ,
        method : 'GET',
        dataType : 'json',
        success : function(data){
            $('#call-data-introduct-api').empty();
            $('#call-data-introduct-api').append(
                data.description
            );
        }
    });
}
introduces.about = function () {
    $.ajax({
        url: url123 + "/api/layouts/" +id ,
        method : 'GET',
        dataType : 'json',
        success : function(data){
            $('#call-data-introduct-api').empty();
            $('#call-data-introduct-api').append(
                data.description
            );
        }
    });
}
// layout
introduces.about = function () {
    $.ajax({
        url: url123 + "/api/introduces" ,
        method : 'GET',
        dataType : 'json',
        success : function(data){
            $('#aboutNav').empty();
            $.each(data, function (index, value) {
                $('#aboutNav').append(
                    `<li> <a class="nav-link" href="/about?tab=${value.id}" >${value.name}</a></li>`
                );
            });
        }
    });
}
introduces.estate = function () {
    $.ajax({
        url: url123 + "/api/categorys" ,
        method : 'GET',
        dataType : 'json',
        success : function(data){
            $('#estateNav').empty();
            $.each(data, function (index, value) {
                if(value.id != 0) {
                    $('#estateNav').append(
                        `<li> <a class="nav-link" href="/estates?id=${value.id}">${value.name}</a></li>`
                    );
                }
            });
        }
    });
}
introduces.news = function () {
    $.ajax({
        url: url123 + "/api/categoryNews" ,
        method : 'GET',
        dataType : 'json',
        success : function(data){
            $('#newsNav').empty();
            $.each(data, function (index, value) {
                if(value.id != 0) {
                    $('#newsNav').append(
                        `<li> <a class="nav-link" href="/categoryNewss?id=${value.id}">${value.name}</a></li>`
                    );
                }
            });
        }
    });
}
introduces.partner = function () {
    $.ajax({
        url: url123 + "/api/partnerss" ,
        method : 'GET',
        dataType : 'json',
        success : function(data){
            $('#partnerNav').empty();
            $.each(data, function (index, value) {
                $('#partnerNav').append(
                    `<li> <a class="nav-link" href="/partners?tab=${value.tab}">${value.name}</a></li>`
                );
            });
        }
    });
}
estates.filterEstate = function () {
    let estateObj = {};

    estateObj.price = $("#price").val();
    $.ajax({
        url: url123 + "/api/filterEstate",
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(estateObj),
        success: function (data) {
            $("#listEstates").empty();
            $.each(data, function (index, estate) {
                let price = `${estate.price}`/1000000000 +"T";
                let year = `${estate.checkin}`.slice(0,4);
                $("#listEstates").append(
                    `<div class="col-12 col-md-6 col-xl-4">
                    <a class="land-figure" href="/estate?id=${estate.id}"><figure class="land-figure-link"><img alt="${estate.name}" data-original="upload/${estate.image}" src="upload/${estate.image}" style="">
                    <div class="land-details-nail">
                    <div class="land-detail-info">
                    <div class="icon"><i class="fa fa-home" aria-hidden="true"></i></div>
                <p>${estate.area}m2</p>
                </div>
                <div class="land-detail-info">
                    <div class="icon end"><i class="fa fa-bed" aria-hidden="true"></i></div>
                <p>1</p>
                </div>
                <div class="land-detail-info">
                    <div class="icon top"><i class="fa fa-bath" aria-hidden="true"></i></div>
                <p>1</p>
                </div>
                <div class="land-detail-info">
                    <div class="icon smaller"><i class="fa fa-calendar-plus-o" aria-hidden="true"></i></div>
                <p>${year}</p>
                </div>
                </div>
                <div class="land-price">${price}</div></figure></a>
                </div>`
                )
            })
        }
    })
};
estates.search = function () {
    let estateObj = {};
    estateObj.price = $("#priceSearch").val();
    // estateObj.area = $("#areaSearch").val();

    $.ajax({
        url: url123 + "/api/filterEstate",
        method: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(estateObj),
        success: function (data) {
            $("#listEstates").empty();
            $.each(data, function (index, estate) {
                let price = `${estate.price}`/1000000000 +"T";
                let year = `${estate.checkin}`.slice(0,4);
                $("#listEstates").append(
                `<div class="col-12 col-md-6 col-xl-4">
                    <a class="land-figure" href="/estate?id=${estate.id}"><figure class="land-figure-link"><img alt="${estate.name}" data-original="upload/${estate.image}" src="upload/${estate.image}" style="">
                    <div class="land-details-nail">
                    <div class="land-detail-info">
                    <div class="icon"><i class="fa fa-home" aria-hidden="true"></i></div>
                <p>${estate.area}m2</p>
                </div>
                <div class="land-detail-info">
                    <div class="icon end"><i class="fa fa-bed" aria-hidden="true"></i></div>
                <p>1</p>
                </div>
                <div class="land-detail-info">
                    <div class="icon top"><i class="fa fa-bath" aria-hidden="true"></i></div>
                <p>1</p>
                </div>
                <div class="land-detail-info">
                    <div class="icon smaller"><i class="fa fa-calendar-plus-o" aria-hidden="true"></i></div>
                <p>${year}</p>
                </div>
                </div>
                <div class="land-price">${price}</div></figure></a>
                </div>`
                )
            });
        }
    })
};
// news
let news = {} || news
news.titleNews = function () {
    $.ajax({
        url: url123 + "/api/news/",
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $('#title').empty();
            $.each(data, function (index, value) {
                if (index < 10) {
                    $('#title').append(
                        `
              <ul class="blog-list">
          <li><a href="/blogDetails?id=${value.id}">${value.title1}</a></li>
        </ul>
           `
                    );
                }
            });

        }
    });
}