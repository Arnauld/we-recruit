Try me!
========================

*Currently tested on MacOSX snow leopard using the Chrome Browser.* Demo is activated on
integration-test phase using the *demo* profile.

    git clone ...

    cd we-recruit/com.podprogramming.jobs.WeRecruit/

Make sure drivers can be executed on your platform (by default chrome is used during tests)

    chmod -R +x drivers/

If you've trouble with the Chrome driver picked, activate the good ones dependending on your os:
availables (not tested yet):

* linux32
* linux64
* mac
* windows

e.g.

    mvn -Pdemo,linux64 integration-test

If you don't have chrome or want to test on different browsers (not tested yet), activate
the one you want:

* ie
* chrome
* firefox
* htmlunit

e.g.

   mvn -Pdemo,linux64,firefox integration-test

Integration Tests
=======================================

Similar to the demo, but uses the *itest* profile instead.

* Launch integration tests

        mvn -Pitest integration-test

* Test on IE

        mvn -Pie,itest integration-test

* Test on Firefox

        mvn -Pfirefox,itest integration-test

* Test on chrome

        mvn -Pchrome,itest integration-test

Notes
=======================================

       git repack && git prune

