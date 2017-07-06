# Create a sequence of numbers between 1 and 40 incrementing by 1.
	x <- seq(1, 40, by = 1)
	# Choose the mean as 2.5 and standard deviation as 0.5.
	y <- dnorm(x, mean = 31.665595769470986, sd = 7.746759570018766)
	print(y)
	write.csv(y,"Result\\TempR\\2_output.csv", row.names = FALSE)