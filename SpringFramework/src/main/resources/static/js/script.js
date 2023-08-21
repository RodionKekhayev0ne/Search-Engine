$(function(){

    const appendBook = function(data){
        var bookCode = '<a href="#" class="book-link" data-id="' +
            data.id + '"></a><br>';
        $('#book-list')
            .append('<div>' + bookCode + '</div>');
    };

    //Loading books on load page
//    $.get('/books/', function(response)
//    {
//        for(i in response) {
//            appendBook(response[i]);
//        }
//    });

    //Show adding book form
    $('#show-add-book-form').click(function(){
        $('#book-form').css('display', 'flex');
    });

    //Closing adding book form
    $('#book-form').click(function(event){
        if(event.target === this) {
            $(this).css('display', 'none');
        }
    });

    //Getting book
    $(document).on('click', '.book-link', function(){
        var link = $(this);
        var bookId = link.data('id');
        $.ajax({
            method: "GET",
            url: '/tasks/' + bookId,
            success: function(response)
            {
                var code = '<span>Task:' + response.task + '</span>';
                link.parent().append(code);
            },
            error: function(response)
            {
                if(response.status == 404) {
                    alert('Задание не найдена!');
                }
            }
        });
        return false;
    });

    //Adding book
     $('#save-book').click(function()
       {
           var data = $('#book-form form').serialize();
          const options = {
            hostname: 'localhost:8080',
            path: '/tasks/' + data,
            method: 'POST',
            success: function(response)
                        {
                            $('#book-form').css('display', 'none');
                            var book = {};
                            book.id = response;
                            appendBook(book);
                        }


          };
           return false;
       });


});
