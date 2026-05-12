//traitement district dynamique
document.getElementById('regionSelect').addEventListener('change', function () {
    const regionId = this.value;
    const districtSelect = document.getElementById('districtSelect');

    // 1. On vide la liste actuelle
    districtSelect.innerHTML = '<option value="">-- Chargement... --</option>';

    if (regionId) {
        const url = '/hello/districts/' + regionId;
        // 2. Appel à l'API de votre contrôleur Spring MVC
        fetch(url)
            .then(response => {
                if (!response.ok) throw new Error('HTTP error ' + response.status);
                return response.json();
            })

            .then(districts => {
                // 3. On vide à nouveau le message de chargement
                districtSelect.innerHTML = '<option value="">-- Sélectionnez un district --</option>';

                // 4. On ajoute les nouveaux districts
                districts.forEach(district => {
                    const option = document.createElement('option');
                    option.value = district.id_district;
                    option.textContent = district.libelle;
                    districtSelect.appendChild(option);
                });
            });
    } else {
        districtSelect.innerHTML = '<option value="">-- Attente de région --</option>';
    }
});

//traitement commune dynamique
document.getElementById('districtSelect').addEventListener('change', function () {
    const regionId = this.value;
    const communeSelect = document.getElementById('communeSelect');

    // 1. On vide la liste actuelle
    communeSelect.innerHTML = '<option value="">-- Chargement... --</option>';

    if (regionId) {
        const url = window.location.pathname.replace(/\/[^\/]*$/, '') + '/communes/' + regionId
        // 2. Appel à l'API de votre contrôleur Spring MVC
        fetch(url)
            .then(response => {
                if (!response.ok) throw new Error('HTTP error ' + response.status);
                return response.json();
            })

            .then(communes => {
                // 3. On vide à nouveau le message de chargement
                communeSelect.innerHTML = '<option value="">-- Sélectionnez un commune --</option>';

                // 4. On ajoute les nouveaux communes
                communes.forEach(commune => {
                    const option = document.createElement('option');
                    option.value = commune.id_commune;
                    option.textContent = commune.libelle;
                    communeSelect.appendChild(option);
                });
            });
    } else {
        communeSelect.innerHTML = '<option value="">-- Attente de région --</option>';
    }
});
