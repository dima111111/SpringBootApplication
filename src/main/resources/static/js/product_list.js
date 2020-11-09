jQuery(document).ready(function($){
    $('[type="remove_button"]').click(function() {
        var productId = $(this).attr("element-id");
        var productName = $('#' + productId).find('[type="product_name"]').text();
        var isDelete = confirm("Are you sure you want delete product " + productName + " ?");
        if (isDelete) {
            $.ajax({
                url : '/products/' + productId + '/',
                type: 'DELETE',
                success : function(response) {
                    $('#' + productId).remove();
                },
                fail: function(xhr, textStatus, errorThrown){
                    alert('request failed');
                }
            });
        }
    });
});