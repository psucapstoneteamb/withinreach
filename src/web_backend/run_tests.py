import sys

if __name__ == '__main__':
    import nose
    argv = sys.argv[:]
    #argv.insert(1, '--nocapture') # nose is sneaky with the 1st arg
    nose.main(argv=argv)
