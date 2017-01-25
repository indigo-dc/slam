module.exports = {
    paths: {
        public: 'target/classes/static',
        watched: ['src/main/resources/static/js']
    },
    modules: {
        definition: false,
        wrapper: false
    },
    npm: {
        enabled: false
    },
    files: {
        javascripts: {
            joinTo: {
                'js/app.js': /^src\/main\/resources\/static\/js/,
                'js/vendor.js': /^bower_components/
            },
            order: {
                before: [
                    'src/main/resources/static/js/indigo.config.js',
                    'src/main/resources/static/js/indigo.routes.js'
                ]
            }
        },
        stylesheets: {
            joinTo: {
                'css/vendor.css': /^bower_components/
            }
        }
    },
    conventions: {
        ignored: []
    },
    plugins: {
    }
};
