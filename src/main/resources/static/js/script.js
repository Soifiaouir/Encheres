const select = document.getElementById('categorie');
select.addEventListener('change', function() {
    const value = select.value;
    if (value) { // si une catégorie est sélectionnée
        window.location.href = '/categorieArticle?idCategorie=' + value;
    }
})