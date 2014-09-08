Genome Simulator
====
Download 'Genome_Simulator_JARFILE' folder to get the binary version of software .

Download 'grapher' folder to get source code.

Download 'Manual.pdf' to get the manual of software . 

To run the jar file 
===
Run  'Genome_Simulator_JARFILE/eclipse/Eclipse'

To run in Eclipse :-
===

(1) Import "grapher" in Eclipse after downloading it from git repo here https://github.com/17patelumang/GenomeSimulator

(2) In Eclipse, 

select Help-->Install New Software--> In "Work With" paste this link http://download.eclipse.org/tools/gef/updates/milestones/ -->(if one cant see GEF(Graphic Editing Framework) under name then , Add-->Location-->http://download.eclipse.org/tools/gef/updates/milestones/) -->Under "Name" check mark the GEF tab-->Next -->Next--> Accept-->Finish

It will take time to install & after restarting eclipse , now most of the error will be solved.

(3) Right click --> Refresh (Now here check if there any error, most version of eclipse wont show error however if it does (esp in eclipse version 'Luna') then it will in 'View.java' (src/com.grapher/View.java) at line 23 "import org.eclipse.osgi.framework.internal.core.ConsoleManager;" & in plugin.xml (plugin.xml tab) last plugin. 

<extension
         id="grapher1"
         point="org.eclipse.core.runtime.products">
      <product
            application="grapher.application">
         <property
               name="appName">
         </property>
      </product>
   </extension>) . Please comment out both the things in View.java and plugin.xml . I have already commented out this on git repo now , however for other version of eclipse even if it is not commented its fine because this dependency is not used in application.

(4) Right click on grapher folder --> Run as --> Eclipse Application and it will run.

To check here click on File-->Open--> Path to admixture.txt(sample file on github to see  the running).
