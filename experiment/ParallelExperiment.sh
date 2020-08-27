#while read parameters                           #this while loop creates a file called program_arguments.txt that stores ALL items from data
#do                                              #...set in the format: stage_day_time (example 3_21_10)
 #       IFS=' '
  #      set $parameters
   #     echo $1
#done < ../data/trivialhill_in.txt > ../output/timeOutputs.txt

echo "Enter the size of your subset:" 
read set_n

cd ..
                                                #n is the size of the subset
count=0
while [ $count -lt $set_n ]                             #I will change the size of n for the different subset sizes
do                                              #this is a while loop that reads from the file with split data and uses each line as an argume
                                                #argument when running LSArray App
        #read splitz
        #IFS='_'
        #set $splitz
        make ParallelBasins "ARGS=data/small_in.txt SequentialTest.txt"
        count=`expr $count + 1`
done > output/ParallelTimeOutputs.txt

echo "The times have been written to time timeOutput.txt" 

